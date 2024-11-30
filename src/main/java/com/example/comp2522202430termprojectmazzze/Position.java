package com.example.comp2522202430termprojectmazzze;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a position in a 2D grid with x and y coordinates.
 * Provides utility methods for calculating distance and equality checks.
 *
 * @author Eunji
 * @version 2024
 */
public class Position implements Serializable {
    private final int coordinateX;
    private final int coordinateY;

    /**
     * Constructs a Position object with the specified coordinates.
     *
     * @param coordinateX the x-coordinate of the position
     * @param coordinateY the y-coordinate of the position
     */
    public Position(final int coordinateX, final int coordinateY) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }

    /**
     * Retrieves the x-coordinate of this position.
     *
     * @return the x-coordinate as an integer
     */
    public int getCoordinateX() {
        return coordinateX;
    }

    /**
     * Retrieves the y-coordinate of this position.
     *
     * @return the y-coordinate as an integer
     */
    public int getCoordinateY() {
        return coordinateY;
    }

    /**
     * Calculates the Euclidean distance from this position to another position.
     *
     * @param other the other Position to calculate the distance to
     * @return the Euclidean distance as a double
     */
    public double distanceTo(final Position other) {
        int directionX = this.getCoordinateX() - other.getCoordinateX();
        int directionY = this.getCoordinateY() - other.getCoordinateY();
        return Math.sqrt(directionX * directionX + directionY * directionY);
    }

    /**
     * Compares this position to another object for equality.
     * Two positions are considered equal if their x and y coordinates are the same.
     *
     * @param obj the object to compare with
     * @return true if the object is a Position with the same coordinates, false otherwise
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Position position = (Position) obj;
        return coordinateX == position.coordinateX && coordinateY == position.coordinateY;
    }

    /**
     * Generates a hash code for this position.
     * The hash code is based on the x and y coordinates.
     *
     * @return the hash code as an integer
     */
    @Override
    public int hashCode() {
        return Objects.hash(coordinateX, coordinateY);
    }

    /**
     * Returns a string representation of the position.
     *
     * @return the formatted string
     */
    @Override
    public String toString() {
        return String.format("Position{x=%d, y=%d}", coordinateX, coordinateY);
    }
}
