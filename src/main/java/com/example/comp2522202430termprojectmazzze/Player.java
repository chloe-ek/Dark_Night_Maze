package com.example.comp2522202430termprojectmazzze;

public class Player implements Movement { private int x, y;

    /**
     * Initializes the player at a specified starting position.
     *
     * @param startX Initial X-coordinate
     * @param startY Initial Y-coordinate
     */
    public Player(int startX, int startY) {
        this.x = startX;
        this.y = startY;
    }

    public int getX() { return x; }
    public int getY() { return y; }


    @Override
    public void move(int dx, int dy, boolean[][] maze) {
        int newX = x + dx;
        int newY = y + dy;

        // 경계 내에 있고 벽이 아닌 경우 이동
        if (newX >= 0 && newX < maze.length && newY >= 0 && newY < maze[0].length && !maze[newX][newY]) {
            x = newX;
            y = newY;
        }
    }
}
