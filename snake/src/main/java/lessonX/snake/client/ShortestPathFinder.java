package lessonX.snake.client;

import java.awt.Point;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;

public class ShortestPathFinder {

    private CustomBoard customBoard;
    private Point source;
    private Point target;
    private boolean[][] visited;
    private Map<Point, Point> parents;

    public ShortestPathFinder() {}

    private ShortestPathFinder(final CustomBoard customBoard,
                               final Point source,
                               final Point target) {
        Objects.requireNonNull(customBoard, "The input customboard is null.");
        Objects.requireNonNull(source, "The source node is null.");
        Objects.requireNonNull(target, "The target node is null.");

        this.customBoard = customBoard;
        this.source = source;
        this.target = target;

        checkSourceNode();
        checkTargetNode();

        this.visited = new boolean[customBoard.getHeight()][customBoard.getWidth()];
        this.parents = new HashMap<>();
        this.parents.put(source, null);
    }

    public List<Point> findPath(final CustomBoard customBoard,
                                final Point source,
                                final Point target,
                                boolean firstY) {
        return new ShortestPathFinder(customBoard, source, target).compute(firstY);
    }

    private List<Point> compute(boolean firstY) {
        final Queue<Point> queue = new ArrayDeque<>();
        final Map<Point, Integer> distances = new HashMap<>();


        queue.add(source);
        distances.put(source, 0);

        while (!queue.isEmpty()) {
            // Removes the head of the queue.
            final Point current = queue.remove();

            if (current.equals(target)) {
                return constructPath();
            }

            for (final Point child : generateChildren(current,firstY)) {
                if (!parents.containsKey(child)) {
                    parents.put(child, current);
                    queue.add(child);
                }
            }
        }
        return null;
    }

    private List<Point> constructPath() {
        Point current = target;
        final List<Point> path = new ArrayList<>();

        while (current != null) {
            path.add(current);
            current = parents.get(current);
        }

        Collections.<Point>reverse(path);
        return path;
    }

    private Iterable<Point> generateChildren(final Point current, boolean firstY) {
        final Point north = new Point(current.x, current.y - 1);
        final Point south = new Point(current.x, current.y + 1);
        final Point west = new Point(current.x - 1, current.y);
        final Point east = new Point(current.x + 1, current.y);

        final List<Point> childList = new ArrayList<>(4);

        if(firstY)
        {
            if (customBoard.cellIsTraversible(west)) {
                childList.add(west);
            }

            if (customBoard.cellIsTraversible(east)) {
                childList.add(east);
            }
            if (customBoard.cellIsTraversible(north)) {
                childList.add(north);
            }

            if (customBoard.cellIsTraversible(south)) {
                childList.add(south);
            }
        }
        else{
            if (customBoard.cellIsTraversible(north)) {
                childList.add(north);
            }

            if (customBoard.cellIsTraversible(south)) {
                childList.add(south);
            }

            if (customBoard.cellIsTraversible(west)) {
                childList.add(west);
            }

            if (customBoard.cellIsTraversible(east)) {
                childList.add(east);
            }
        }



        return childList;
    }

    private void checkSourceNode() {
        checkNode(source,
                "The source node (" + source + ") is outside the customboard. " +
                        "The width of the customboard is " + customBoard.getWidth() + " and " +
                        "the height of the customboard is " + customBoard.getHeight() + ".");

        if (!customBoard.cellIsFree(source.x, source.y)) {
            throw new IllegalArgumentException(
                    "The source node (" + source + ") is at a occupied cell.");
        }
    }

    private void checkTargetNode() {
        checkNode(target,
                "The target node (" + target + ") is outside the customboard. " +
                        "The width of the customboard is " + customBoard.getWidth() + " and " +
                        "the height of the customboard is " + customBoard.getHeight() + ".");

        if (!customBoard.cellIsFree(target.x, target.y)) {
            throw new IllegalArgumentException(
                    "The target node (" + target + ") is at a occupied cell.");
        }
    }

    private void checkNode(final Point node, final String errorMessage) {
        if (node.x < 0
                || node.x >= customBoard.getWidth()
                || node.y < 0
                || node.y >= customBoard.getHeight()) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public List<Point> nextStep(int[][] boardplan, int headx, int heady, int applex, int appley, boolean firstY) {
        try
        {

            int[][] boardPlan = boardplan;

            boolean[][] bBoard = new boolean[boardPlan.length][boardPlan[0].length];

            for (int i = 0; i < bBoard.length; ++i) {
                for (int j = 0; j < bBoard[i].length; ++j) {
                    bBoard[i][j] = boardPlan[i][j] > 0;
                }
            }


            final CustomBoard customBoard = new CustomBoard(bBoard);
            final Point source = new Point(headx,heady); // Same as new Point(0, 0):
            final Point target = new Point(applex, appley);


            long startTime = System.nanoTime();
            final List<Point> path = new ShortestPathFinder().findPath(customBoard,
                    source,
                    target,firstY);
            long endTime  = System.nanoTime();
            return (path);

        }
        catch(NullPointerException e)
        {
            ArrayList<Point> arrayList = new ArrayList<Point>();
            return arrayList;
        }

    }
}