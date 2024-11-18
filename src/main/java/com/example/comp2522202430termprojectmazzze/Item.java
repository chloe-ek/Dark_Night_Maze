package com.example.comp2522202430termprojectmazzze;

public class Item {
    private final int itemX;
    private final int itemY;

    public Item(final Position position) {
        this.itemX = position.getX();
        this.itemY = position.getY();
    }

    public int getX() {
        return itemX;
    }

    public int getY() {
        return itemY;
    }

}
