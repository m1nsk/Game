package game.model.dwellers.interfaces;

import game.model.dwellers.DwellersType;

/**
 * Created by korolm on 13.12.2017.
 */
public interface IDwellerHelper {

    int getPriority();

    DwellersType getType();

    String getIcon();
}
