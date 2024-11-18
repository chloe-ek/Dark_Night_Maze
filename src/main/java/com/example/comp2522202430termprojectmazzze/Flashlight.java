package com.example.comp2522202430termprojectmazzze;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Flashlight {
    private final Player player;
    private int radius;
    private final Color color;
    private final double opacity;

    /**
     * Initializes the Flashlight with a specified radius, color, and opacity.
     *
     * @param player  The player associated with this flashlight
     * @param radius  Radius of the flashlight effect
     * @param color   Color of the flashlight
     * @param opacity Opacity level (0.0 - 1.0)
     */
    public Flashlight(final Player player, final int radius, final Color color, final double opacity) {
        this.player = player;
        this.radius = radius;
        this.color = color;
        this.opacity = opacity;
    }

    public int getRadius() {
        return radius;
    }

    public void increaseRadius() {
        radius++;
    }


    /**
     * Draws the flashlight effect on the canvas at the specified position.
     *
     * @param gc       The GraphicsContext to draw on
     * @param tileSize The size of each tile in pixels
     */
    public void draw(final GraphicsContext gc, final int tileSize) {
        double originalAlpha = gc.getGlobalAlpha();
        gc.setFill(color);
        gc.setGlobalAlpha(opacity);

        int playerX = player.getPosition().getX();
        int playerY = player.getPosition().getY();

        gc.fillOval((playerX - radius) * tileSize, (playerY - radius) * tileSize,
                radius * 2 * tileSize, radius * 2 * tileSize);

        gc.setGlobalAlpha(originalAlpha);
    }
}
