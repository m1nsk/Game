package game.model.dwellers;

import game.model.Field;

/**
 * Created by korolm on 12.12.2017.
 */
public class Tree extends AbstractDweller{
    final static int BREEDING_SIZE = 4;
    final static int BREEDING_DISTANCE = 4;


    public Tree(Field field) {
        size = 0;
        priority = 0;
        type = DwellersType.TREE;
        this.field = field;
    }

    @Override
    protected void breed() {
        if (size >= BREEDING_SIZE) {
            if (generateNewDweller(BREEDING_DISTANCE, new Tree(field)))
                size = 0;
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
}
