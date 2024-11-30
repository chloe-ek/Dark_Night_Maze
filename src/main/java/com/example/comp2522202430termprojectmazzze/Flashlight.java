package com.example.comp2522202430termprojectmazzze;

import javafx.scene.paint.Color;
import java.io.Serializable;

/**
 * Represents a flashlight that highlights a circular area around the player.
 *
 * @author Eunji
 * @version 2024
 */
public class Flashlight implements Serializable {
    private final Player player;
    private final int radius;
    private final transient Color color;
    private final double opacity;

    /**
     * Initializes the Flashlight with a specified radius, color, and opacity.
     *
     * @param player  The player associated with this flashlight
     * @param radius  Radius of the flashlight effect as an int
     * @param color   Color of the flashlight
     * @param opacity Opacity level (0.0 - 1.0) as a double
     */
    public Flashlight(final Player player, final int radius,
                      final Color color, final double opacity) {
        this.player = player;
        this.radius = radius;
        this.color = color;
        this.opacity = opacity;
    }

    /**
     * Returns the radius of the flashlight effect.
     *
     * @return the radius of the flashlight in tiles
     */
    public int getRadius() {
        return radius;
    }

    /**
     * Returns the player associated with the flashlight.
     *
     * @return the player object linked to this flashlight
     */

    public Player getPlayer() {
        return player;
    }

    /**
     * Returns the color of the flashlight glow.
     *
     * @return the color of the flashlight
     */
    public Color getColor() {
        return color;
    }

    /**
     * Returns the opacity level of the flashlight effect.
     *
     * @return the opacity level as a double, where 0.0 is fully transparent
     *         and 1.0 is fully opaque
     */
    public double getOpacity() {
        return opacity;
    }

}
