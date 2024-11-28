package com.example.comp2522202430termprojectmazzze;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

import java.io.Serializable;


public class Item implements Renderer, Serializable {
    private static final long serialVersionUID = 1L;

    private final Position position;
    private transient Image itemImage;


    public Item(final Position position) {
        this.position = position;
        this.itemImage = ImageLoader.loadImage("/images/item.png");

    }

    private Object readResolve() {
        itemImage = ImageLoader.loadImage("/images/item.png"); // 복원
        return this;
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
