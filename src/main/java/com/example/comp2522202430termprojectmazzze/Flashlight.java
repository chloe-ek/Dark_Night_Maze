package com.example.comp2522202430termprojectmazzze;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * The Flashlight class represents a circular light effect centered on a player.
 * It creates a flashlight effect by drawing a semi-transparent circle around a position.
 *
 */
public class Flashlight {
    private int x, y;
    private int radius;
    private Color color;
    private double opacity;

    /**
     * Initializes the Flashlight with a specified radius, color, and opacity.
     *
     * @param radius  Radius of the flashlight effect
     * @param color   Color of the flashlight
     * @param opacity Opacity level (0.0 - 1.0)
     */
    public Flashlight(final int radius, final Color color, final double opacity) {
        this.radius = radius;
        this.color = color;
        this.opacity = opacity;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Draws the flashlight effect on the canvas at the specified position.
     *
     * @param gc       The GraphicsContext to draw on
     * @param tileSize The size of each tile in pixels
     */
    public void draw(GraphicsContext gc, int tileSize) {
        gc.setFill(color);
        gc.setGlobalAlpha(opacity);
        gc.fillOval((x - radius) * tileSize, (y - radius) * tileSize, radius * 2 * tileSize, radius * 2 * tileSize);
        gc.setGlobalAlpha(1.0);
    }
}
