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


    public Ghost(final Position startPosition, final long moveInterval) {
        this.position = startPosition;
        this.moveInterval = moveInterval;
    }

    @Override
    public Position getPosition() {
        return position;
    }


    @Override
    public void update(final boolean[][] maze) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastMoveTime >= moveInterval) {
            Direction randomDirection = getRandomDirection();
            move(randomDirection, maze);
            lastMoveTime = currentTime;
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
