package com.example.comp2522202430termprojectmazzze;

import java.util.Random;

public class Ghost implements Movement {
    private int x, y;
    private final Random random = new Random();
    private long lastMoveTime = 0;
    private long moveInterval;

    public Ghost(final int startX, final int startY, final long moveInterval) {
        this.x = startX;
        this.y = startY;
        this.moveInterval = moveInterval;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    // 랜덤 이동 메서드
    public void randomMove(boolean[][] maze) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastMoveTime >= moveInterval) {
            int dx = random.nextInt(3) - 1; // -1, 0, 1 중 하나
            int dy = random.nextInt(3) - 1;
            move(dx, dy, maze);
            lastMoveTime = currentTime;
        }
    }

    public void move(int dx, int dy, boolean[][] maze) {
        int newX = x + dx;
        int newY = y + dy;
        if (newX >= 0 && newX < maze.length && newY >= 0 && newY < maze[0].length && !maze[newX][newY]) {
            x = newX;
            y = newY;
        }
    }

}
