package game.model;
import game.model.dwellers.*;

import java.awt.Point;
import java.util.PriorityQueue;
import java.util.Comparator;

/**
 * Created by korolm on 12.12.2017.
 */
public class Field {
    private int FIELD_SIZE = 20;

    private DwellerObserver[][] dwellers = new DwellerObserver[FIELD_SIZE][FIELD_SIZE];

    private PriorityQueue<DwellerObserver> dwellerQueue = new PriorityQueue<>(dwellerComparator);

    public Field() {
        FIELD_SIZE = 20;
        initDefaultDwellers();
    }

    public Field(int fieldSize) {
        FIELD_SIZE = fieldSize;
        initDefaultDwellers();
    }

    public static Comparator<DwellerObserver> dwellerComparator = (DwellerObserver d1, DwellerObserver d2)->Integer.compare(d1.getPriority(), d2.getPriority());

    private void initDefaultDwellers() {
        for(int i = 0; i < FIELD_SIZE; i++) {
            for(int i = 0; i < FIELD_SIZE; i++) {
                dwellers = null;
            }
        }
    }

    private boolean isFieldEnable(Point point) {
        if (isFieldEmpty() && isFieldAllowed())
            return true;
        return false;
    }

    private boolean isFieldAllowed(Point point) {
        if (point.x < FIELD_SIZE && point.x > 0 && point.y < FIELD_SIZE && point.y > 0)
            return true;
        return false;
    }

    private boolean isFieldEmpty(Point point) {
        if (dwellers[point.x][point.y] == null)
            return false;
        return true;
    }

    public boolean setNewDweller(Point point, DwellerObserver dweller){
        if (isFieldEnable(point)){
            dwellers[point.x][point.y] = dweller;
            return true;
        }
        return false;
    }

};
