package com.example.comp2522202430termprojectmazzze;

import javafx.scene.canvas.GraphicsContext;


/**
 * Provides rendering capabilities for objects within the game.
 *
 * @author Eunji
 * @version 2024
 */
public interface Renderer {

    /**
     * Renders the object onto the given graphics context.
     *
     * @param gc the graphics context to draw on as a GraphicsContext
     * @param tileSize the size of each tile in pixels as an int
     */
    void render(GraphicsContext gc, int tileSize);
}
