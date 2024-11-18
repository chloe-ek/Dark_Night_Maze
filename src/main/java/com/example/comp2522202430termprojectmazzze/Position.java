package com.example.comp2522202430termprojectmazzze;


public class Position {
    private final int coordinateX;
    private final int coordinateY;

    public Position(final int coordinateX, final int coordinateY) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }

    public int getX() {
        return coordinateX;
    }

    public int getY() {
        return coordinateY;
    }
}