package lessonX.snake.client;
import java.awt.Point;
import java.util.List;
import java.util.Objects;

public class CustomBoard {

    private static final boolean CELL_OCCUPIED = true;

    private final boolean[][] customboard;

    public CustomBoard(final boolean[][] customboard) {
        Objects.requireNonNull(customboard, "The input customboard is null.");

        final int numberOfRows = customboard.length;

        if (numberOfRows == 0) {
            throw new IllegalArgumentException("The input customboard is empty.");
        }

        int numberOfColumns = 0;

        for (int row = 0; row < customboard.length; ++row) {
            numberOfColumns = Math.max(numberOfColumns, customboard[row].length);
        }

        this.customboard = new boolean[numberOfRows][numberOfColumns];

        for (int row = 0; row < numberOfRows; ++row) {
            for (int column = 0;
                 column < Math.min(numberOfColumns, customboard[row].length);
                 column++) {
                this.customboard[row][column] = customboard[row][column];
            }
        }
    }

    public int getWidth() {
        return customboard[0].length;
    }

    public int getHeight() {
        return customboard.length;
    }

    public boolean cellIsFree(final Point p) {
        return cellIsFree(p.x, p.y);
    }

    public boolean cellIsWithincustomboard(final Point p) {
        return p.x >= 0 && p.x < getWidth() && p.y >= 0 && p.y < getHeight();
    }

    public boolean cellIsTraversible(final Point p) {
        return cellIsWithincustomboard(p) && cellIsFree(p);
    }

    public boolean cellIsFree(final int x, final int y) {
        checkXCoordinate(x);
        checkYCoordinate(y);
        return customboard[y][x] != CELL_OCCUPIED;
    }

    public String withPath(final List<Point> path) {
        final char[][] matrix = new char[getHeight()][getWidth()];

        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix[0].length; ++j) {
                matrix[i][j] = customboard[i][j] ? 'x' : '.';
            }
        }

        for (final Point p : path) {
            matrix[p.y][p.x] = 'o';
        }

        final StringBuilder sb = new StringBuilder();

        sb.append(new String(matrix[0]));

        for (int i = 1; i < matrix.length; ++i) {
            sb.append('\n');
            sb.append(new String(matrix[i]));
        }

        return sb.toString();
    }

    private void checkXCoordinate(final int x) {
        if (x < 0) {
            throw new IndexOutOfBoundsException(
                    "The x-coordinate is negative: " + x + ".");
        }

        if (x >= customboard[0].length) {
            throw new IndexOutOfBoundsException(
                    "The x-coordinate is too large (" + x +
                            "). The amount of columns in this customboard is " +
                            customboard[0].length + ".");
        }
    }

    private void checkYCoordinate(final int y) {
        if (y < 0) {
            throw new IndexOutOfBoundsException(
                    "The y-coordinate is negative: " + y + ".");
        }

        if (y >= customboard.length) {
            throw new IndexOutOfBoundsException(
                    "The y-coordinate is too large (" + y +
                            "). The amount of rows in this customboard is " +
                            customboard.length + ".");
        }
    }
}