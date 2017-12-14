package game.model.dwellers;

import game.algorithms.WaveTrace;
import game.model.Field;
import interfaces.DwellerObserver;
import interfaces.impl.AbstractDweller;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by korolm on 12.12.2017.
 */
public class Rabbit extends AbstractDweller {
    private final static int BREEDING_SIZE = 3;
    private final static int BREEDING_DISTANCE = 5;
    private final static int HUNGER_LIMIT = 4;
    private final static ArrayList<DwellersType> VICTIMS = new ArrayList<>(Arrays.asList(DwellersType.TREE));

    private int hunger;



    public Rabbit(Field field) {
        size = 0;
        priority = 1;
        hunger = 0;
        type = DwellersType.RABBIT;
        this.field = field;
    }

    private boolean hunt() {
        Point newPoint = WaveTrace.getTurn(field, position, VICTIMS);
        if (newPoint == null) {
            return false;
        }
        DwellerObserver pointDweller = field.getDweller(newPoint);
        if (pointDweller != null) {
            kill(newPoint, pointDweller);
            field.moveDweller(position, newPoint, this);
            return true;
        }
        field.moveDweller(position, newPoint, this);
        return false;
    }

    private void kill(Point point, DwellerObserver dweller){
        field.removeDweller(point, dweller);
    }

    @Override
    protected void breed() {
        if (size >= BREEDING_SIZE) {
            if (generateNewDweller(BREEDING_DISTANCE, new Rabbit(field)))
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
