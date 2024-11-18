package com.example.comp2522202430termprojectmazzze;

public final class Player implements Movement {
    private int currentX;
    private int currentY;

    public Player(final int startX, final int startY) {
        this.currentX = startX;
        this.currentY = startY;
    }

    public int getCurrentX() {
        return currentX; }
    public int getCurrentY() {
        return currentY; }

    private boolean isValidMove(final int targetX, final int targetY, final boolean[][] maze) {
        return targetX >= 0 && targetX < maze.length
                && targetY >= 0 && targetY < maze[0].length
                && !maze[targetX][targetY];
    }

    @Override
    public void move(final int directionX, final int directionY, final boolean[][] maze) {
        int nextX = currentX + directionX;
        int nextY = currentY + directionY;

        if (isValidMove(nextX, nextY, maze)) {
            currentX = nextX;
            currentY = nextY;

        }
    }
}
