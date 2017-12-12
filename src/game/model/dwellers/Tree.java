package game.model.dwellers;

import game.model.Field;

import java.awt.*;

/**
 * Created by korolm on 12.12.2017.
 */
public class Tree extends AbstractDweller implements DwellerObserver {
    final static int BREEDING_SIZE = 4;
    final static int BREEDING_DISTANCE = 4;


    public Tree(Field field) {
        size = 0;
        this.field = field;
    }

    @Override
    protected void breed() {
        if (this.size > BREEDING_SIZE) {
            generateNewDweller();
        }
    }

    @Override
    protected void death() {

    }

    @Override
    protected void turn() {
        size++;
        breed();
    }

    @Override
    public void nextTurn() {
        turn();
    }

    @Override
    public int getPriority(){
        return 0;
    }

    protected Tree generateNewDweller() {
        Point ltCorner = new Point(point.x - BREEDING_DISTANCE, point.y - BREEDING_DISTANCE);
        while (true) {
            if (field.setNewDweller(new Point(ltCorner.x + ran.nextInt(BREEDING_DISTANCE), ltCorner.y + ran.nextInt(BREEDING_DISTANCE)), new Tree(field))) {
                break;
            }
        }
    }
}
