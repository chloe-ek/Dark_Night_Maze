package com.example.comp2522202430termprojectmazzze;


import java.io.Serializable;

public class Position implements Serializable {
    private final int coordinateX;
    private final int coordinateY;

    public Position(final int coordinateX, final int coordinateY) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }

    public int getCoordinateX() {
        return coordinateX;
    }

    public int getCoordinateY() {
        return coordinateY;
    }

    public double distanceTo(final Position other) {
        int directionX = this.getCoordinateX() - other.getCoordinateX();
        int directionY = this.getCoordinateY() - other.getCoordinateY();
        return Math.sqrt(directionX * directionX + directionY * directionY);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Position position = (Position) obj;

        return getCoordinateX() == position.getCoordinateX() && getCoordinateY()
                == position.getCoordinateY();
    }
}
