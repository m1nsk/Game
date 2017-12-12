package game.model.dwellers;

import game.model.Field;
/**
 * Created by korolm on 12.12.2017.
 */
public interface DwellerObserver {
    public void nextTurn();

    public int getPriority();
}
