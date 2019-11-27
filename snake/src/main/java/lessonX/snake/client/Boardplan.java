package lessonX.snake.client;

import com.codenjoy.dojo.services.Point;

import java.util.ArrayList;
import java.util.List;

public class Boardplan {

    public static int[][] filledBoardPlan(List<Point> snake, Point tail, Point stone, Point head, ArrayList<List<Integer>> newSnake){
        int[][] board = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        };
        int tailX = tail.getX()-1;
        int tailY = 13-tail.getY();
        int headX = head.getX()-1;
        int headY = 13-head.getY();
        int stoneX = stone.getX()-1;
        int stoneY = 13-stone.getY();

        for (com.codenjoy.dojo.services.Point ch : snake) {
            int snakeY = (13-ch.getY());
            int snakeX = (ch.getX()-1);
            board[snakeY][snakeX] = 1;
        }
        board[stoneY][stoneX] = 1;
        board[headY][headX] = 0;
        board[tailY][tailX] = 0;
        return board;


    }

}
