package game.view;

import game.model.Field;
import game.model.dwellers.interfaces.DwellerObserver;

import java.awt.*;

/**
 * Created by korolm on 13.12.2017.
 */
public class ConsoleVisualize implements IVisualize {
    @Override
    public void show(Field field) {
        for(int i = 0; i < field.getSize(); i++) {
            for(int j = 0; j < field.getSize(); j++) {
                DwellerObserver dweller = field.getDweller(new Point(i, j));
                if (dweller == null)
                    System.out.print(" ");
                else
                    System.out.print(dweller.getIcon());
            }
            System.out.print("\n");
        }
    }
}
