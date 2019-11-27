package lessonX.snake.client;

import com.codenjoy.dojo.services.Direction;

public class NextStep {
    public static String getStep(int thisX, int thisY, int nextStepX, int nextStepY, String currentDirection)
    {
        if (thisY == nextStepY && thisX > nextStepX) {
            return Direction.LEFT.toString();
        }
        if (thisY == nextStepY && thisX <  nextStepX) {
            return Direction.RIGHT.toString();
        }
        if (thisY < nextStepY && thisX == nextStepX) {
            return Direction.UP.toString();
        }
        if (thisY > nextStepY && thisX ==  nextStepX) {
            return Direction.DOWN.toString();
        }
        if (thisY == nextStepY && thisX ==  nextStepX) {
            return currentDirection;
        }
        return "DOWN";
    }
}
