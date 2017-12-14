package game.algorithms;

import game.model.Field;
import game.model.dwellers.DwellersType;
import interfaces.DwellerObserver;

import java.awt.Point;
import java.util.ArrayDeque;
import java.util.ArrayList;

/**
 * Created by korolm on 12.12.2017.
 */
public class WaveTrace {
    /**
     * This is the Lee algorithm (https://en.wikipedia.org/wiki/Lee_algorithm) implementation.
     * the difference is we don't know the final point of path,
     * that's why we trying to find proper cell during search cycle.
     */
    /**
     *Here is array of all Moore neighborhood vectors array
     */
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
        /**
         * Actually some of our endpoints can move every turn that's why we return only first turn of path.
         * We have two queues currentTurns is current deep turns query.
         * nextTurns is query we use to add deep + 1 turns
         * We search proper cell till we find it or till we get round all field in this case we return null
         */
        int depth = 0;
        int size = field.getSize();
        int[][] pMatrix = new int[size][size];
        ArrayDeque<Point> currentTurns = new ArrayDeque<>();
        ArrayDeque<Point> nextTurns = new ArrayDeque<>();
        initDefaultArray(pMatrix);
        currentTurns.push(start);
        depth++;
        while (currentTurns.size() > 0) {
            while (currentTurns.size() > 0) {
                Point turnPoint = currentTurns.poll();
                for (Point turn: turns) {
                    Point newPoint = new Point(turnPoint.x + turn.x, turnPoint.y + turn.y);
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
        /**
         * Here is an algorithm of restoring path from proper cell to start point,
         * but we return only first turn in path
         */
        Point resultPoint = new Point(finish.x, finish.y);
        int cycleDepth = depth;
        while (--cycleDepth >= 0) {
            for (Point turn: turns) {
                Point newPoint = new Point(resultPoint.x + turn.x, resultPoint.y + turn.y);
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
