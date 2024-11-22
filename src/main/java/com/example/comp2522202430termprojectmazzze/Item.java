package com.example.comp2522202430termprojectmazzze;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Item implements Renderer {
    private final Position position;

    public Item(final Position position) {
        this.position = position;

    }

    public Position getPosition() {
        return position;
    }



    @Override
    public void render(final GraphicsContext gc, final int tileSize) {
        gc.setFill(Color.GREEN);
        gc.fillRect(position.getX() * tileSize + tileSize / 4,
                position.getY() * tileSize + tileSize / 4,
                tileSize / 2,
                tileSize / 2);
    }

}
