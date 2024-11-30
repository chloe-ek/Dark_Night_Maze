package com.example.comp2522202430termprojectmazzze;

/**
 * Defines the ability to move within a game environment.
 * Classes implementing this interface can move in specified directions
 * based on the game's maze structure.
 *
 * @author Eunji
 * @version 2024
 */
public interface Movable {

    /**
     * Moves the object in a specified direction within the maze.
     *
     * @param direction the direction to move
     * @param maze the maze structure indicating valid movement paths as a boolean
     */
    void move(Direction direction, boolean[][] maze);

}
