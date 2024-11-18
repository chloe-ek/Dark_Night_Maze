package com.example.comp2522202430termprojectmazzze;

import javafx.scene.image.Image;
import java.util.Random;

public class Ghost implements Movement {
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


    public Position getPosition() {
        return position;
    }

    public Image getGhostImage() {
        return ghostImage;
    }


    // 랜덤 이동 메서드
    public void randomMove(final boolean[][] maze) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastMoveTime >= moveInterval) {
            int directionX = random.nextInt(3) - 1; // -1, 0, 1 중 하나
            int directionY = random.nextInt(3) - 1;
            move(directionX, directionY, maze);
            lastMoveTime = currentTime;
        }
    }

    @Override
    public void move(final int directionX, final int directionY, final boolean[][] maze) {
        int newX = position.getX() + directionX;
        int newY = position.getY() + directionY;

        if (newX >= 0 && newX < maze.length && newY >= 0 && newY < maze[0].length) {
            this.position = new Position(newX, newY);
        }
    }

}
