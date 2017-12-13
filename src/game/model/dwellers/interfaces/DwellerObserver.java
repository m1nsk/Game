package game.model.dwellers.interfaces;

import game.model.Field;
import game.model.dwellers.DwellersType;

import java.awt.*;

/**
 * Created by korolm on 12.12.2017.
 */
public interface DwellerObserver {
    void nextTurn(Point point);

    int getPriority();

    DwellersType getType();
}
