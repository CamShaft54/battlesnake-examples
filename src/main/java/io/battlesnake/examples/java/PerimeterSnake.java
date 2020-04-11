package io.battlesnake.examples.java;

import io.battlesnake.core.AbstractBattleSnake;
import io.battlesnake.core.AbstractSnakeContext;
import io.battlesnake.core.AbstractStrategy;
import io.battlesnake.core.MoveRequest;
import io.battlesnake.core.MoveResponse;
import io.battlesnake.core.Position;
import io.battlesnake.core.StartRequest;
import io.battlesnake.core.StartResponse;
import io.battlesnake.core.Strategy;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

import static io.battlesnake.core.JavaConstants.DOWN;
import static io.battlesnake.core.JavaConstants.LEFT;
import static io.battlesnake.core.JavaConstants.RIGHT;
import static io.battlesnake.core.JavaConstants.UP;

public class PerimeterSnake extends AbstractBattleSnake<PerimeterSnake.SnakeContext> {

  public static void main(String[] args) {
    new PerimeterSnake().run(8080);
  }

  @Override
  public SnakeContext snakeContext() {
    return new SnakeContext();
  }

  @Override
  public Strategy<SnakeContext> gameStrategy() {
    return new AbstractStrategy<SnakeContext>(true) {

      @Override
      public StartResponse onStart(SnakeContext context, StartRequest request) {
        // Add moves that get the snake to origin
        Position pos = request.getYou().getHeadPosition();
        context.addToPath(pos.getX(), LEFT)
            .addToPath(pos.getY(), UP);

        return new StartResponse("#ff00ff", "beluga", "bolt");
      }

      @Override
      public MoveResponse onMove(SnakeContext context, MoveRequest request) {
        // If the snake is at the origin, add the moves for a lap around the perimeter
        if (request.isAtOrigin()) {
          int width = request.getBoard().getWidth();
          int height = request.getBoard().getHeight();
          context.addToPath(width - 1, RIGHT)
              .addToPath(height - 1, DOWN)
              .addToPath(width - 1, LEFT)
              .addToPath(height - 1, UP);
        }

        // Remove a move from the head of the list
        return context.path.remove(0);
      }
    };
  }

  static class SnakeContext extends AbstractSnakeContext {

    private final List<MoveResponse> path = new LinkedList<>();

    private SnakeContext addToPath(int count, MoveResponse response) {
      IntStream.range(0, count).forEach(i -> path.add(response));
      return this;
    }
  }
}
