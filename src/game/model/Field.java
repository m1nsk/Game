package game.model;
import game.Factory.DwellerFactory;
import game.model.dwellers.DwellersType;
import interfaces.DwellerObserver;
import game.view.ConsoleVisualize;

import java.awt.Point;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.Scanner;

/**
 * Created by korolm on 12.12.2017.
 */
public class Field {
    private final int fieldSize;

    private DwellerObserver[][] dwellers;

    private PriorityQueue<DwellerPositionTuple> dwellerQueue = new PriorityQueue<>(dwellerComparator);
    private PriorityQueue<DwellerPositionTuple> newDwellerQueue = new PriorityQueue<>(dwellerComparator);

    public Field() {
        this(20);
    }

    public Field(int fieldSize) {
        this.fieldSize = fieldSize;
        dwellers = new DwellerObserver[fieldSize][fieldSize];
        initDefaultDwellers();
    }

    public boolean removeDweller(Point point, DwellerObserver dweller) {
        if (isFieldAllowed(point)){
            dwellers[point.x][point.y] = null;
            dwellerQueue.remove(new DwellerPositionTuple(dweller, point));
            newDwellerQueue.remove(new DwellerPositionTuple(dweller, point));
            return true;
        }
        return false;
    }

    public int getSize() {
        return fieldSize;
    }

    public void moveDweller(Point oldPoint, Point newPoint, DwellerObserver dweller) {
        removeDweller(oldPoint, dweller);
        addDweller(newPoint, dweller);
    }

    public DwellerObserver getDweller(Point point){
        if (isFieldAllowed(point)){
            return dwellers[point.x][point.y];
        }
        return null;
    }

    private static Comparator<DwellerPositionTuple> dwellerComparator = (DwellerPositionTuple d1, DwellerPositionTuple d2)->Integer.compare(d1.getDweller().getPriority(), d2.getDweller().getPriority());

    private void newTurnNotify(){
        while (dwellerQueue.size() > 0) {
            DwellerPositionTuple dweller = dwellerQueue.poll();
            newDwellerQueue.add(dweller);
            dweller.getDweller().nextTurn(dweller.getPosition());
        }
        dwellerQueue.addAll(newDwellerQueue);
        newDwellerQueue.clear();
    }

    private void initDefaultDwellers() {
        for(int i = 0; i < dwellers.length; i++) {
            for(int j = 0; j < dwellers.length; j++) {
                dwellers[i][j] = null;
            }
        }
    }

    private boolean isFieldEnable(Point point) {
        if (isFieldAllowed(point)) {
            if (isFieldEmpty(point))
                return true;
        }
        return false;
    }

    public boolean isFieldAllowed(Point point) {
        if (point.x < fieldSize && point.x >= 0 && point.y < fieldSize && point.y >= 0)
            return true;
        return false;
    }

    public boolean isFieldEmpty(Point point) {
        if (dwellers[point.x][point.y] == null)
            return true;
        return false;
    }

    public boolean addDweller(Point point, DwellerObserver dweller){
        if(dweller == null)
            return false;
        if (isFieldEnable(point)){
            dwellers[point.x][point.y] = dweller;
            newDwellerQueue.add(new DwellerPositionTuple(dweller, point));
            return true;
        }
        return false;
    }

    private class DwellerPositionTuple{
        DwellerObserver dweller;
        Point position;

        public DwellerPositionTuple(DwellerObserver dweller, Point position) {
            this.dweller = dweller;
            this.position = position;
        }

        public DwellerObserver getDweller() {
            return dweller;
        }

        public Point getPosition() {
            return position;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            DwellerPositionTuple that = (DwellerPositionTuple) o;

            if (dweller != null ? !dweller.equals(that.dweller) : that.dweller != null) return false;
            return position != null ? position.equals(that.position) : that.position == null;

        }

        @Override
        public int hashCode() {
            int result = dweller != null ? dweller.hashCode() : 0;
            result = 31 * result + (position != null ? position.hashCode() : 0);
            return result;
        }
    }

    public static void main(String[] args) {
        int n = 0;
        Field field = new Field();
        ConsoleVisualize consoleView = new ConsoleVisualize();
        field.addDweller(new Point(10, 15), DwellerFactory.createDweller(DwellersType.TREE,field));
        field.addDweller(new Point(12, 12), DwellerFactory.createDweller(DwellersType.TREE,field));
        field.addDweller(new Point(10, 8), DwellerFactory.createDweller(DwellersType.TREE,field));
        field.addDweller(new Point(0, 0), DwellerFactory.createDweller(DwellersType.RABBIT,field));
        field.addDweller(new Point(0, 12), DwellerFactory.createDweller(DwellersType.RABBIT,field));
        field.addDweller(new Point(12, 11), DwellerFactory.createDweller(DwellersType.RABBIT,field));
        field.addDweller(new Point(16, 11), DwellerFactory.createDweller(DwellersType.WOLF,field));
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        while(n != 99) {
            System.out.println("Enter a number: ");
            n = reader.nextInt(); // Scans the next token of the input as an int.
//once finished
            consoleView.show(field);
            field.newTurnNotify();

        }
        reader.close();
    }

}
