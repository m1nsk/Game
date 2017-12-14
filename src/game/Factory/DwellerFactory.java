package game.Factory;

import game.model.Field;
import game.model.dwellers.DwellersType;
import game.model.dwellers.Rabbit;
import game.model.dwellers.Tree;
import game.model.dwellers.Wolf;
import interfaces.DwellerObserver;

public class DwellerFactory {
    public static DwellerObserver createDweller(DwellersType type, Field field){
        if(type == DwellersType.TREE)
            return new Tree(field);
        if(type == DwellersType.WOLF)
            return new Wolf(field);
        if(type == DwellersType.RABBIT)
            return new Rabbit(field);
        return null;
    }
}
