package com.example.comp2522202430termprojectmazzze;


/**
 * Represents an object that can update its state based on the game environment.
 *
 * @author Eunji
 * @version 2024
 */
public interface Updatable {

    /**
     * Updates the object's state based on the current maze structure.
     *
     * @param maze the maze structure representing valid paths as a boolean
     */
    void update(boolean[][] maze);

}
