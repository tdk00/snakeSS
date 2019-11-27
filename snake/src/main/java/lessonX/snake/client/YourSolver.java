package lessonX.snake.client;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2018 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


import com.codenjoy.dojo.client.Solver;
import com.codenjoy.dojo.client.WebSocketRunner;
import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.RandomDice;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.codenjoy.dojo.services.PointImpl.pt;

/**
 * User: your name
 */
public class YourSolver implements Solver<Board> {

  private Dice dice;
  private Board board;
  private Boardplan boardplan;
  private NextStep nextStep;
  private ImaginaryBoard imaginaryBoard;
  ShortestPathFinder shortestPathFinder = new ShortestPathFinder();

  public YourSolver(Dice dice) {
    this.dice = dice;
  }
  public static ArrayList<List<Integer>> NewSnake = new ArrayList<List<Integer>>();
  static {
    List<Integer> CurrentList =  new ArrayList<Integer>();
    CurrentList.add(7);
    CurrentList.add(6);
    NewSnake.add(CurrentList);
  }
  @Override
  public String get(Board board) {
    this.board = board;
    com.codenjoy.dojo.services.Point apple = board.getApples().get(0);
    if(board.getHead()==null){
      System.out.println("GET HEAD IS NULL");
      return "RIGHT";
    }
    if(board.getSnake().size()<3){
      NewSnake.clear();
    }
    System.out.println("New Snake:"+NewSnake);
    com.codenjoy.dojo.services.Point head = board.getHead();
    com.codenjoy.dojo.services.Point stone = board.getStones().get(0);
    com.codenjoy.dojo.services.Point tail = board.getSnakeTail().get(0);
    List<com.codenjoy.dojo.services.Point> Snake = board.getSnake();
    final int[][] boardPlan = boardplan.filledBoardPlan(Snake,tail,stone,head,null);
    List<Point> nextDir;
    String currentDirection = board.getSnakeDirection().toString();
    if(board.getSnake().size()>55){
      boardPlan[13 - apple.getY()][apple.getX() - 1] = 1;
      apple=stone;
      boardPlan[13 - stone.getY()][stone.getX() - 1] = 0;
    }

    List<Point> pathToApple;
    List<Point> pathToAppleY = getNextDirection(shortestPathFinder, boardPlan, head.getX(), head.getY(), apple.getX(), apple.getY(),true);
    List<Point> pathToAppleX = getNextDirection(shortestPathFinder, boardPlan, head.getX(), head.getY(), apple.getX(), apple.getY(),false);
    List<Point> pathToTail = getNextDirection(shortestPathFinder, boardPlan, head.getX(), head.getY(), tail.getX(), tail.getY(),true);


    System.out.println(Arrays.deepToString(boardPlan));
    System.out.println(); System.out.println();


    if(pathToAppleY==null && pathToAppleX==null){
      nextDir=pathToTail;
      int distance = 0;
      com.codenjoy.dojo.services.Point longest = null;
      for (int x = 1; x < 14; x++) {
        for (int y = 1; y < 14; y++) {
          if (boardPlan[13-y][x-1]==1) continue;
          com.codenjoy.dojo.services.Point pt = pt(x, y);
          List<Point> longPath = getNextDirection(shortestPathFinder,boardPlan,head.getX(),head.getY(),pt.getX(),pt.getY(),true);

          if(longPath==null) continue;
          if(longPath.size()<2) continue;
          int nextHY3 = (int) (13 - longPath.get(1).getY());
          int nextHX3 = (int) (longPath.get(1).getX() + 1);
          boardPlan[13 - head.getY()][head.getX() - 1] = 1;
          List<Point> fromNextStepToTail2 = getNextDirection(shortestPathFinder, boardPlan, nextHX3, nextHY3, tail.getX(), tail.getY(),true);
          boardPlan[13 - head.getY()][head.getX() - 1] = 0;
          if(fromNextStepToTail2==null) continue;
          if (distance < longPath.size()) {
            distance = longPath.size();
            longest = pt;
          }
        }
      }
      if(longest!=null){
        List<Point> longPath = getNextDirection(shortestPathFinder,boardPlan,head.getX(),head.getY(),longest.getX(),longest.getY(),true);
        nextDir = longPath;
      }

    }
    else {
      pathToApple = pathToAppleY;
      BoardVO boardPlanWhenCatchApple = imaginaryBoard.pathExists(boardPlan, pathToAppleY, NewSnake);
      Point[] a= boardPlanWhenCatchApple.getHeadAndTail();
      int[][] a2;
      a2 = boardPlanWhenCatchApple.getBoard7();
      System.out.println("Head and Tail Will In:"+Arrays.deepToString(a));
      System.out.println(("HeadX:"+a[0].x)+(" HeadY:"+a[0].y));
      a2[a[0].y][a[0].x] = 0;
      a2[13-a[1].y][a[1].x-1] = 0;
      List<Point> imaginatedPath =
              getNextDirection(shortestPathFinder, a2, a[0].x+1, 13-a[0].y, a[1].x, a[1].y,true);
      if(imaginatedPath==null){
        boardPlanWhenCatchApple = imaginaryBoard.pathExists(boardPlan, pathToAppleX, NewSnake);
        a = boardPlanWhenCatchApple.getHeadAndTail();
        a2 = boardPlanWhenCatchApple.getBoard7();
        System.out.println("Head and Tail Will In:"+Arrays.deepToString(a));
        System.out.println(("HeadX:"+a[0].x)+(" HeadY:"+a[0].y));
        a2[a[0].y][a[0].x] = 0;
        a2[13-a[1].y][a[1].x-1] = 0;
        imaginatedPath =
                getNextDirection(shortestPathFinder, a2, a[0].x+1, 13-a[0].y, a[1].x, a[1].y,true);
        if(imaginatedPath!=null){
          pathToApple = pathToAppleX;
        }
      }
      System.out.println(imaginatedPath);
      int nextHY2 = (int) (13 - pathToApple.get(1).getY());
      int nextHX2 = (int) (pathToApple.get(1).getX() + 1);
      boardPlan[13 - head.getY()][head.getX() - 1] = 1;
      List<Point> fromNextStepToTail = getNextDirection(shortestPathFinder, boardPlan, nextHX2, nextHY2, tail.getX(), tail.getY(),true);
      if(fromNextStepToTail==null){
        nextDir = pathToTail;
      }
      else {
        nextDir = pathToApple;
      }
      boardPlan[13 - head.getY()][head.getX() - 1] = 0;
      if(imaginatedPath!=null){
        nextDir = pathToApple;
        System.out.println("Almaya catanda quyruga yol olacaq");
      }else{
        int distance = 0;
        com.codenjoy.dojo.services.Point longest = null;
        for (int x = 1; x < 14; x++) {
          for (int y = 1; y < 14; y++) {
            if (boardPlan[13-y][x-1]==1) continue;
            com.codenjoy.dojo.services.Point pt = pt(x, y);
            List<Point> longPath = getNextDirection(shortestPathFinder,boardPlan,head.getX(),head.getY(),pt.getX(),pt.getY(),true);

            if(longPath==null) continue;
            if(longPath.size()<2) continue;
            int nextHY3 = (int) (13 - longPath.get(1).getY());
            int nextHX3 = (int) (longPath.get(1).getX() + 1);
            boardPlan[13 - head.getY()][head.getX() - 1] = 1;
            List<Point> fromNextStepToTail2 = getNextDirection(shortestPathFinder, boardPlan, nextHX3, nextHY3, tail.getX(), tail.getY(),true);
            boardPlan[13 - head.getY()][head.getX() - 1] = 0;
            if(fromNextStepToTail2==null) continue;
            if (distance < longPath.size()) {
              distance = longPath.size();
              longest = pt;
            }
          }
        }
        if(longest!=null){
          List<Point> longPath = getNextDirection(shortestPathFinder,boardPlan,head.getX(),head.getY(),longest.getX(),longest.getY(),true);
          nextDir = longPath;
        }else
        {
          nextDir = pathToTail;
        }
      }
    }
    if(nextDir==null){
      return "DOWN";
    }
    int nextHY = (int) (13 - nextDir.get(1).getY());
    int nextHX = (int) (nextDir.get(1).getX() + 1);
    boolean ifNextApple = false;
    boolean ifNextStone = false;
    if(apple.getX()==nextHX && apple.getY()==nextHY){
      ifNextApple = true;
      if(board.getSnake().size()>55){
         ifNextStone = true;
      }
    }
    NewSnake = saveSnake(nextHX,nextHY,NewSnake,ifNextApple,ifNextStone);

    String nextStep =  NextStep.getStep(head.getX(),head.getY(),nextHX,nextHY,currentDirection);
    return nextStep;
  }
  public static void main(String[] args) {
    WebSocketRunner.runClient(
            // paste here board page url from browser after registration
            "http://104.248.23.201/codenjoy-contest/board/player/4edh9lwpbexkcnldl6nj?code=2213238390061172754",
            new YourSolver(new RandomDice()),
            new Board());
  }

  public  List<Point> getNextDirection(ShortestPathFinder shortestPathFinder, int[][] boardPlan, int headx, int heady, int applex, int appley, boolean firstY){
   final List<Point> point =
            (List<Point>) shortestPathFinder.nextStep(boardPlan, headx - 1, 13 - heady, applex - 1, 13 - appley,firstY);
    return point;

  }

  public ArrayList<List<Integer>> saveSnake (int nextX, int nextY, ArrayList<List<Integer>> snakeArray, boolean ifNextApple, boolean ifNextStone){

    ArrayList<List<Integer>> NewSnake = snakeArray;
    List<Integer> NextList =  new ArrayList<Integer>();
    if(NewSnake.size()<=2){
      NewSnake.clear();
      List<Integer> CurrentList =  new ArrayList<Integer>();
      CurrentList.add(board.getSnake().get(0).getX());
      CurrentList.add(board.getSnake().get(0).getY());
      NewSnake.add(CurrentList);
      CurrentList.clear();
      CurrentList.add(board.getSnake().get(1).getX());
      CurrentList.add(board.getSnake().get(1).getY());
      NewSnake.add(CurrentList);
    }
    NextList.add(nextX);
    NextList.add(nextY);
    NewSnake.add(0, NextList);
    if(!ifNextApple){
      NewSnake.remove(NewSnake.size() - 1);
    }
    else
    {
      if(ifNextStone==true){
        NewSnake.remove(NewSnake.size() - 1);
        for (int i = 1; i<=10; i++){
          NewSnake.remove(NewSnake.size() - i);
        }
      }
    }
    return NewSnake;
  }

}