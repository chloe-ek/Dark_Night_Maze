package com.example.comp2522202430termprojectmazzze;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents an item in the maze that can be collected by the player.
 * The item has a position in the maze and can be rendered on the game canvas.
 * It also supports serialization for saving and loading game state.
 *
 * @author Eunji
 * @version 2024
 */
public class Item implements Renderer, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final Position position;
    private transient Image itemImage;

    /**
     * Constructs an Item with the specified position in the maze.
     *
     * @param position the position of the item in the maze as a Position object
     */
    public Item(final Position position) {
        this.position = position;
        this.itemImage = ImageLoader.getInstance().loadImage("/images/item.png");

    }

    /**
     * Initializes transient fields upon deserialization.
     *
     * @return the deserialized Item object
     */
    @Serial
    private Object readResolve() {
        itemImage = ImageLoader.getInstance().loadImage("/images/item.png");
        return this;
    }

    /**
     * Retrieves the position of the item.
     *
     * @return the position of the item as a Position object
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Renders the item on the game canvas.
     * If the item image is unavailable, a green rectangle is drawn instead.
     *
     * @param gc the GraphicsContext used to render the item
     * @param tileSize the size of a single tile in pixels
     */
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
