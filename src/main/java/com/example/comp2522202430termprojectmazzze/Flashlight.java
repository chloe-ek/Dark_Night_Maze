package com.example.comp2522202430termprojectmazzze;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.*;

import java.io.Serializable;


public class Flashlight implements Serializable {
    private final Player player;
    private int radius;
    private final transient Color color;
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

    public Player getPlayer() {
        return player;
    }



    /**
     * Draws the flashlight effect on the canvas at the specified position.
     *
     * @param gc       The GraphicsContext to draw on
     * @param tileSize The size of each tile in pixels
     */
    public void draw(final GraphicsContext gc, final int tileSize) {
        double adjustedRadius = radius * tileSize;
        double playerX = player.getPosition().getCoordinateX() * tileSize;
        double playerY = player.getPosition().getCoordinateY() * tileSize;

        // Define the radial gradient for the flashlight effect
        RadialGradient gradient = new RadialGradient(
                0,  // Focus angle
                0,  // Focus distance
                playerX + tileSize / 2,  // Center X
                playerY + tileSize / 2,  // Center Y
                adjustedRadius,          // Radius
                false,                   // Proportional
                CycleMethod.NO_CYCLE,    // No repetition of gradient
                new Stop(0, color),      // Center color
                new Stop(1, Color.BLACK) // Edge color
        );

        // Save current settings
        Paint originalFill = gc.getFill();
        double originalAlpha = gc.getGlobalAlpha();

        // Apply the gradient
        gc.setFill(gradient);
        gc.setGlobalAlpha(opacity);

        // Draw the flashlight effect
        gc.fillOval(playerX - adjustedRadius, playerY - adjustedRadius,
                adjustedRadius * 2, adjustedRadius * 2);

        // Restore original settings
        gc.setFill(originalFill);
        gc.setGlobalAlpha(originalAlpha);
    }
}
