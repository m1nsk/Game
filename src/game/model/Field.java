package game.model;
import game.model.dwellers.Rabbit;
import game.model.dwellers.Tree;
import game.model.dwellers.Wolf;
import game.model.dwellers.interfaces.DwellerObserver;
import game.view.ConsoleVisualize;

import java.awt.Point;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.Scanner;

/**
 * Created by korolm on 12.12.2017.
 */
public class Field {
    private int FIELD_SIZE = 20;

    private DwellerObserver[][] dwellers = new DwellerObserver[FIELD_SIZE][FIELD_SIZE];

    private PriorityQueue<DwellerPositionTuple> dwellerQueue = new PriorityQueue<>(dwellerComparator);
    private PriorityQueue<DwellerPositionTuple> newDwellerQueue = new PriorityQueue<>(dwellerComparator);

    public Field() {
        FIELD_SIZE = 20;
        initDefaultDwellers();
    }

    public Field(int fieldSize) {
        FIELD_SIZE = fieldSize;
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
        return FIELD_SIZE;
    }

    public void moveDweller(Point oldPoint, Point newPoint, DwellerObserver dweller) {
        removeDweller(oldPoint, dweller);
        dwellers[oldPoint.x][oldPoint.y] = null;
        addDweller(newPoint, dweller);
        dwellers[newPoint.x][newPoint.y] = dweller;
    }

    public DwellerObserver getDweller(Point point){
        if (isFieldAllowed(point)){
            return dwellers[point.x][point.y];
        }
        return null;
    }

    private static Comparator<DwellerPositionTuple> dwellerComparator = (DwellerPositionTuple d1, DwellerPositionTuple d2)->Integer.compare(d1.getDweller().getPriority(), d2.getDweller().getPriority());

    void newTurnNotify(){
        while (dwellerQueue.size() > 0) {
            DwellerPositionTuple dweller = dwellerQueue.poll();
            newDwellerQueue.add(dweller);
            dweller.getDweller().nextTurn(dweller.getPosition());
        }
        dwellerQueue.addAll(newDwellerQueue);
        newDwellerQueue.clear();
    }

    private void initDefaultDwellers() {
        for(int i = 0; i < FIELD_SIZE; i++) {
            for(int j = 0; j < FIELD_SIZE; j++) {
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
        if (point.x < FIELD_SIZE && point.x >= 0 && point.y < FIELD_SIZE && point.y >= 0)
            return true;
        return false;
    }

    public boolean isFieldEmpty(Point point) {
        if (dwellers[point.x][point.y] == null)
            return true;
        return false;
    }

    public boolean addDweller(Point point, DwellerObserver dweller){
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
        field.addDweller(new Point(10, 15), new Tree(field));
        field.addDweller(new Point(7, 18), new Tree(field));
        field.addDweller(new Point(5, 5), new Tree(field));
        field.addDweller(new Point(7, 13), new Tree(field));
        field.addDweller(new Point(5, 8), new Tree(field));
        field.addDweller(new Point(12, 12), new Rabbit(field));
        field.addDweller(new Point(10, 8), new Rabbit(field));
        field.addDweller(new Point(0, 0), new Wolf(field));
        field.addDweller(new Point(0, 12), new Wolf(field));
        field.addDweller(new Point(12, 11), new Wolf(field));
        field.addDweller(new Point(16, 11), new Wolf(field));
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
