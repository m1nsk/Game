package game.view;

import game.model.Field;
import game.model.dwellers.DwellersType;
import interfaces.DwellerObserver;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by korolm on 13.12.2017.
 */
public class ConsoleVisualize implements IVisualize {

    private final static HashMap<DwellersType, String> typeIconDict;
    static {
        typeIconDict = new HashMap<>();
        typeIconDict.put(DwellersType.TREE, "t");
        typeIconDict.put(DwellersType.RABBIT, "r");
        typeIconDict.put(DwellersType.WOLF, "w");
    }

    @Override
    public void show(Field field) {
        for(int i = 0; i < field.getSize(); i++) {
            for(int j = 0; j < field.getSize(); j++) {
                DwellerObserver dweller = field.getDweller(new Point(i, j));
                if (dweller == null)
                    System.out.print(" ");
                else
                    System.out.print(typeIconDict.getOrDefault(dweller.getType(), " "));
            }
            System.out.print("\n");
        }
    }
}
