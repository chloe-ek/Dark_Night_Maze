package com.example.comp2522202430termprojectmazzze;


public class Position {
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

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Position position = (Position) obj;

        return getCoordinateX() == position.getCoordinateX() && getCoordinateY() == position.getCoordinateY();
    }
}
