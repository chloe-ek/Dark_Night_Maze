package com.example.comp2522202430termprojectmazzze;

import javafx.scene.input.KeyCode;

public enum Direction {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    private final int directionX;
    private final int directionY;

    Direction(final int directionX, final int directionY) {
        this.directionX = directionX;
        this.directionY = directionY;
    }

    public int getDirectionX() {
        return directionX;
    }

    public int getDirectionY() {
        return directionY;
    }


    public static Direction fromKeyCode(final KeyCode keyCode) {
        return switch (keyCode) {
            case UP -> UP;
            case DOWN -> DOWN;
            case LEFT -> LEFT;
            case RIGHT -> RIGHT;
            default -> null;
        };
    }
}
