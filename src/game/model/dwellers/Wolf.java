package game.model.dwellers;

import game.algorithms.WaveTrace;
import game.model.Field;
import game.model.dwellers.interfaces.DwellerObserver;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by korolm on 12.12.2017.
 */
public class Wolf extends AbstractDweller{
    final static int BREEDING_SIZE = 3;
    final static int BREEDING_DISTANCE = 5;
    final static int HUNGER_LIMIT = 6;
    final static ArrayList<DwellersType> VICTIMS = new ArrayList<>(Arrays.asList(DwellersType.RABBIT));

    private int hunger;



    public Wolf(Field field) {
        size = 0;
        priority = 1;
        hunger = 0;
        icon = "w";
        type = DwellersType.WOLF;
        this.field = field;
    }

    private boolean hunt() {
        Point newPoint = WaveTrace.getTurn(field, position, VICTIMS);
        if (newPoint == null) {
            return false;
        }
        DwellerObserver pointDweller = field.getDweller(newPoint);
        if (pointDweller != null) {
            field.removeDweller(newPoint, pointDweller);
            field.moveDweller(position, newPoint, this);
            return true;
        }
        field.moveDweller(position, newPoint, this);
        return false;
    }

    @Override
    protected void breed() {
        if (size >= BREEDING_SIZE) {
            if (generateNewDweller(BREEDING_DISTANCE, new Wolf(field)))
                size = 0;
        }
    }

    @Override
    protected void turn() {
        hunger++;
        if (hunger >= HUNGER_LIMIT) {
            death();
            return;
        }
        if (hunt()) {
            size++;
            hunger = 0;
        }
        breed();
    }
}
