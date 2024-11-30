package com.example.comp2522202430termprojectmazzze;

import javafx.scene.input.KeyCode;

/**
 * Represents the four cardinal directions for movement within the game.
 *
 * @author Eunji
 * @version 2024
 */
public enum Direction {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    private final int directionX;
    private final int directionY;

    /**
     * Constructs a Direction with the specified x and y changes.
     *
     * @param directionX the change in the x-coordinate as an int
     * @param directionY the change in the y-coordinate as an int
     */
    Direction(final int directionX, final int directionY) {
        this.directionX = directionX;
        this.directionY = directionY;
    }

    /**
     * Gets the change in the x-coordinate associated with this direction.
     *
     * @return the change in the x-coordinate as an int
     */
    public int getDirectionX() {
        return directionX;
    }

    /**
     * Gets the change in the y-coordinate associated with this direction.
     *
     * @return the change in the y-coordinate as an int
     */
    public int getDirectionY() {
        return directionY;
    }

    /**
     * Determines the Direction corresponding to a given keyboard key code.
     *
     * @param keyCode the key code input as a KeyCode
     * @return the corresponding Direction, or null if the key code
     * is not associated with any direction
     */
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
