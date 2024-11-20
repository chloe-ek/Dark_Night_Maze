package com.example.comp2522202430termprojectmazzze;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Random;

public class Ghost implements Character {
    private static final String IMAGE_PATH = "/images/ghost.png";
    private Position position;
    private Image ghostImage;
    private final Random random = new Random();
    private long lastMoveTime = 0;
    private final long moveInterval;


    public Ghost(final Position startPosition, final long moveInterval) {
        this.position = startPosition;
        this.moveInterval = moveInterval;

        try {
            this.ghostImage = new Image(getClass().getResource(IMAGE_PATH).toExternalForm());
        } catch (NullPointerException e) {
            System.out.println("Ghost image not found");
            this.ghostImage = null;
        }
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
    public void move(Direction direction, boolean[][] maze) {
        int newX = position.getX() + direction.getDirectionX();
        int newY = position.getY() + direction.getDirectionY();


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
            gc.drawImage(ghostImage, position.getX() * tileSize, position.getY() * tileSize, tileSize, tileSize);
        } else {
            gc.setFill(Color.WHITE);
            gc.fillOval(position.getX() * tileSize, position.getY() * tileSize, tileSize, tileSize);
        }
    }


}
