package game.model.dwellers;

import game.model.Field;
import game.model.dwellers.interfaces.DwellerObserver;

import java.awt.Point;
import java.util.HashSet;
import java.util.Random;

/**
 * Created by korolm on 12.12.2017.
 */
abstract class AbstractDweller implements DwellerObserver{
    final static Random ran = new Random();

    protected int size;

    protected Field field;

    protected Point position;

    protected int priority;

    protected DwellersType type;

    @Override
    public void nextTurn(Point point) {
        position = point;
        turn();
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public DwellersType getType() {
        return type;
    }

    abstract protected void breed();

    protected void death() {
        field.removeDweller(position, this);
    };

    abstract protected void turn();

    protected boolean generateNewDweller(final int distance, final DwellerObserver dweller) {
        int dDistance = distance - 1;
        Point ltCorner = new Point(position.x - dDistance, position.y - dDistance);
        HashSet<Point> pSet = new HashSet<>();
        int counter = 0;
        dDistance = dDistance * 2 + 1;
        while (counter < Math.pow(dDistance, 2)) {
            Point nextPoint = new Point(ltCorner.x + ran.nextInt(dDistance), ltCorner.y + ran.nextInt(dDistance));
            if (pSet.contains(nextPoint))
                continue;
            pSet.add(nextPoint);
            counter++;
            if (field.addDweller(nextPoint, dweller)) {
                System.out.println(nextPoint.x + " " + nextPoint.y + " : new Dweller");
                return true;
            }
        }
        return false;
    }
}
