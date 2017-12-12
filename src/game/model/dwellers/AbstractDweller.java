package game.model.dwellers;

import game.model.Field;
import java.awt.Point;
import java.util.Random

/**
 * Created by korolm on 12.12.2017.
 */
abstract class AbstractDweller {
    final static Random ran = new Random();

    protected int size;

    protected Field field;

    protected Point point;

    abstract protected void breed();

    abstract protected void death();

    abstract protected void turn();
}
