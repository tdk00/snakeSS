package lessonX.snake.client;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ImaginaryBoard {
    public static BoardVO pathExists(int[][] Board, List<Point> path, ArrayList<List<Integer>> newSnake){
        int[][] Board2 = {
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
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
        Point tail = null;
        Point head = null;
        for (int i1=0; i1<Board.length;i1++) {
            for(int j1=0; j1<Board[i1].length;j1++){
                  Board2[i1][j1] = Board[i1][j1];
            }
        }

        if(path.size()-1>newSnake.size()){
            Board2 = removeSnakeFromBoard(Board2,newSnake);
            for (int i=1;i<=newSnake.size()+1;i++) {
                Board2[(int) path.get(path.size()-i).getY()][(int) path.get(path.size()-i).getX()] = 1;
                if(i==newSnake.size()+1){
                    tail = new Point ((int) path.get(path.size()-i).getX()+1,13-(int) path.get(path.size()-i).getY());
                    head = new Point (path.get(path.size()-1));
                }
            }
        }
        else
        {
            for (int i=1;i<=path.size();i++) {
                Board2[(int) path.get(path.size()-i).getY()][(int) path.get(path.size()-i).getX()] = 1;

                    tail = new Point ((newSnake.get(newSnake.size()-(path.size()-1)).get(0)),(newSnake.get(newSnake.size()-(path.size()-1)).get(1)));
                    head = new Point (path.get(path.size()-1));

                if(i==path.size() || i==path.size()-1) continue;
                Board2[13-newSnake.get(newSnake.size()-i).get(1)][newSnake.get(newSnake.size()-i).get(0)-1] = 0;

            }
        }
        Point[] a = {head,tail};
        BoardVO vo = new BoardVO();
        vo.setHeadAndTail(a);
        vo.setBoard7(Board2);
        return vo;
    }


    public static int[][] removeSnakeFromBoard(int[][] board, ArrayList<List<Integer>> newSnake){
        for (List<Integer> ch: newSnake) {
            board[13-ch.get(1)][ch.get(0)-1] = 0;
        }
        return board;
    }
}
