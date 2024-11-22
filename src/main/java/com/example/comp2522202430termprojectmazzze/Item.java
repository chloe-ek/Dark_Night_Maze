package com.example.comp2522202430termprojectmazzze;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

import java.util.Objects;


public class Item implements Renderer {
    private final Position position;
    private final Image itemImage;


    public Item(final Position position) {
        this.position = position;
        this.itemImage = new Image(Objects.requireNonNull(getClass().
                getResourceAsStream("/images/item.png")));

    }

    public Position getPosition() {
        return position;
    }


    @Override
    public void render(final GraphicsContext gc, final int tileSize) {
        if (itemImage != null) {
            gc.drawImage(itemImage,
                    position.getCoordinateX() * tileSize,
                    position.getCoordinateY() * tileSize, tileSize, tileSize);
        } else {
            gc.setFill(Color.GREEN);
            gc.fillRect(position.getCoordinateX() * tileSize,
                    position.getCoordinateY() * tileSize, tileSize, tileSize);

        }
    }

}
