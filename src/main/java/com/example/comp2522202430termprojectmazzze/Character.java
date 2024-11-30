package com.example.comp2522202430termprojectmazzze;

/**
 * Represents a game character that can move and be rendered on the screen.
 *
 * @author Eunji
 * @version 2024
 */
public interface Character extends Movable, Renderer {

    /**
     * Gets the current position of the character in the game.
     *
     * @return the character's position as a Position object
     */
    Position getPosition();
}
