package com.example.comp2522202430termprojectmazzze;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.Random;

public class Ghost implements Character, Updatable, Serializable {
    private static final String IMAGE_PATH = "/images/ghost.png";
    private Position position;
    private static Image ghostImage = ImageLoader.getInstance().loadImage(IMAGE_PATH);
    private final Random random = new Random();
    private long lastMoveTime = 0;
    private final long moveInterval;
    private final GameLogic gameLogic;


    public Ghost(final Position startPosition, final long moveInterval, final GameLogic gameLogic) {
        this.position = startPosition;
        this.moveInterval = moveInterval;
        this.gameLogic = gameLogic;
    }

    @Override
    public Position getPosition() {
        return position;
    }


    @Override
    public void update(final boolean[][] maze) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastMoveTime >= moveInterval) {
            double distanceToPlayer = position.distanceTo(gameLogic.getPlayerPosition());

            if (distanceToPlayer <= GameLogic.getGhostDetectionRadius()) {
                // 플레이어 추적
                moveTowardPlayer(maze, gameLogic.getPlayerPosition());
            } else {
                // 랜덤 이동
                Direction randomDirection = getRandomDirection();
                move(randomDirection, maze);
            }
            lastMoveTime = currentTime;
        }
    }

    private void moveTowardPlayer(final boolean[][] maze, final Position playerPosition) {
        int dx = playerPosition.getCoordinateX() - position.getCoordinateX();
        int dy = playerPosition.getCoordinateY() - position.getCoordinateY();

        Direction direction = null;

        // 우선 이동할 방향 결정
        if (Math.abs(dx) > Math.abs(dy)) {
            direction = dx > 0 ? Direction.RIGHT : Direction.LEFT;
        } else if (dy != 0) {
            direction = dy > 0 ? Direction.DOWN : Direction.UP;
        }

        // 유효한 방향으로 이동
        if (direction != null) {
            move(direction, maze);
        }
    }


    @Override
    public void move(final Direction direction, final boolean[][] maze) {
        int newX = position.getCoordinateX() + direction.getDirectionX();
        int newY = position.getCoordinateY() + direction.getDirectionY();


        if (newX >= 0 && newX < maze.length && newY >= 0 && newY < maze[0].length) {
            position = new Position(newX, newY);
        }
    }

    private Direction getRandomDirection() {
        int dx = random.nextInt(3) - 1; // -1, 0, 1 중 하나
        int dy = random.nextInt(3) - 1;

        for (Direction direction : Direction.values()) {
            if (direction.getDirectionX() == dx && direction.getDirectionY() == dy) {
                return direction;
            }
        }
        return Direction.UP;
    }

    @Override
    public void render(final GraphicsContext gc, final int tileSize) {
        if (ghostImage != null) {
            gc.drawImage(ghostImage, position.getCoordinateX() * tileSize, position.getCoordinateY() * tileSize, tileSize, tileSize);
        } else {
            gc.setFill(Color.WHITE);
            gc.fillOval(position.getCoordinateX() * tileSize, position.getCoordinateY() * tileSize, tileSize, tileSize);
        }
    }


}
