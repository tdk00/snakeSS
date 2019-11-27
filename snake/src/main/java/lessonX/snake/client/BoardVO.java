package lessonX.snake.client;

import java.awt.*;
import java.io.Serializable;

public class BoardVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private int[][] board7;
    private Point[] headAndTail;




    public int[][] getBoard7() {
        return board7;
    }

    public void setBoard7(int[][] board7) {
        this.board7 = board7;
    }

    public Point[] getHeadAndTail() {
        return headAndTail;
    }

    public void setHeadAndTail(Point[] headAndTail) {
        this.headAndTail = headAndTail;

    }
}