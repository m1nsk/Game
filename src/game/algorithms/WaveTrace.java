package game.algorithms;

import game.model.Field;
import game.model.dwellers.DwellersType;
import game.model.dwellers.interfaces.DwellerObserver;

import java.awt.*;
import java.util.*;

/**
 * Created by korolm on 12.12.2017.
 */
public class WaveTrace {
    private static Point[] turns = {
            new Point(0,1),
            new Point(1,0),
            new Point(-1, 0),
            new Point(0, -1),
            new Point(1, 1),
            new Point(-1, -1),
            new Point(1, -1),
            new Point(-1, 1)
    };
    public static Point getTurn(final Field field, final Point start, final ArrayList<DwellersType> victims) {
        int depth = 0;
        int size = field.getSize();
        int[][] pMatrix = new int[size][size];
        ArrayDeque<Point> currentTurns = new ArrayDeque<>();
        ArrayDeque<Point> nextTurns = new ArrayDeque<>();
        initDefaultArray(pMatrix);
        currentTurns.push(start);
        depth++;
        while (currentTurns.size() > 0 || nextTurns.size() > 0) {
            while (currentTurns.size() > 0) {
                Point turnPoint = currentTurns.poll();
                for (int i = 0; i < turns.length; i++) {
                    Point newPoint = new Point(turnPoint.x + turns[i].x, turnPoint.y + turns[i].y);
                    if (!(field.isFieldAllowed(newPoint)))
                        continue;
                    DwellerObserver pointDweller = field.getDweller(newPoint);
                    if (pointDweller != null) {
                        if (victims.contains(pointDweller.getType())) {
                            pMatrix[newPoint.x][newPoint.y] = depth;
                            return restorePath(pMatrix, newPoint, start, depth);
                        }
                        continue;
                    }
                    if (!start.equals(newPoint) && pMatrix[newPoint.x][newPoint.y] == 0) {
                        pMatrix[newPoint.x][newPoint.y] = depth;
                        nextTurns.push(newPoint);
                    }
                }
            }
            currentTurns.addAll(nextTurns);
            nextTurns.clear();
            depth++;
        }
        return null;
    }

    private static void initDefaultArray(final int[][] array) {
        for(int i = 0; i < array.length; i++) {
            for(int j = 0; j < array.length; j++) {
                array[i][j] = 0;
            }
        }
    }

    private static boolean isFieldAllowed(final int[][] array, final Point point) {
        if (point.x < array.length && point.x >= 0 && point.y < array.length && point.y >= 0)
            return true;
        return false;
    }

    private static Point restorePath(final int[][] array, final Point finish,final Point start, final int depth) {
        Point resultPoint = new Point(finish.x, finish.y);
        int cycleDepth = depth;
//        for(int[] list: array) {
//            for(int item: list) {
//                System.out.print(item);
//            }
//            System.out.print("\n");
//        }
        while (--cycleDepth >= 0) {
            for (int i = 0; i < turns.length; i++) {
                Point newPoint = new Point(resultPoint.x + turns[i].x, resultPoint.y + turns[i].y);
                if (isFieldAllowed(array, newPoint)) {
                    if (array[newPoint.x][newPoint.y] + 1 == array[resultPoint.x][resultPoint.y]) {
                        if (newPoint.equals(start)) {
                            return resultPoint;
                        }
                        if (array[newPoint.x][newPoint.y] != 0)
                            resultPoint = newPoint;
                    }
                }
            }
        }
        return start;
    }
}
