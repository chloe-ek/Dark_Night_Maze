package com.example.comp2522202430termprojectmazzze;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Player implements Character, Updatable {
    private Position position;


    public Player(final Position startPosition) {
        this.position = startPosition;

    }
    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void move(final Direction direction, final boolean[][] maze) {
        int newX = position.getX() + direction.getDirectionX();
        int newY = position.getY() + direction.getDirectionY();

        if (isValidMove(newX, newY, maze)) {
            position = new Position(newX, newY);
        }
    }


    private boolean isValidMove(final int targetX, final int targetY, final boolean[][] maze) {
        return targetX >= 0 && targetX < maze.length
                && targetY >= 0 && targetY < maze[0].length
                && !maze[targetX][targetY];
    }

    @Override
    public void render(final GraphicsContext gc, int tileSize) {
        gc.setFill(Color.BLUE);
        gc.fillRect(position.getX() * tileSize, position.getY() * tileSize, tileSize, tileSize);
    }

    @Override
    public void update(final boolean[][] maze) {

    }

}
