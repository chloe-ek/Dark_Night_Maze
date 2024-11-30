package com.example.comp2522202430termprojectmazzze;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

/**
 * Utility class for saving and loading the game state using serialization.
 * Provides methods to persist the game state to a file and load it back.
 *
 * @author eunji
 * @version 2024
 */
public final class GameSaver {

    /**
     * Prevent instantiation of this utility class.
     *
     * @throws UnsupportedOperationException if instantiation is attempted
     */
    private GameSaver() {
        throw new UnsupportedOperationException(
                "GameSaver is a utility class and cannot be instantiated.");
    }

    /**
     * Saves the current game state to a specified file path.
     *
     * @param gameLogic the GameLogic object representing the current game state
     * @param filePath the path to save the game state as a String
     */
    public static void saveGame(final GameLogic gameLogic, final String filePath) {
        File file = new File(filePath);
        File parentDir = file.getParentFile();

        if (parentDir != null && !parentDir.exists() && !parentDir.mkdirs()) {
            System.err.println("Failed to create directories for path: " + parentDir);
            return;
        }

        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameLogic);
            System.out.println("Game saved successfully to: " + filePath);
        } catch (IOException ioException) {
            System.err.println("Error saving game: " + ioException.getMessage());
        }
    }

    /**
     * Loads the game state from a specified file path.
     *
     * @param filePath the path to load the game state from as a String
     * @return the deserialized GameLogic object, or null if an error occurs
     */
    public static GameLogic loadGame(final String filePath) {
        File saveFile = new File(filePath);
        if (!saveFile.exists()) {
            System.err.println("Save file does not exist: " + filePath);
            return null;
        }

        try (FileInputStream fis = new FileInputStream(filePath);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (GameLogic) ois.readObject();
        } catch (IOException ioException) {
            System.err.println("Error reading save file: " + ioException.getMessage());
        } catch (ClassNotFoundException classNotFoundException) {
            System.err.println("Class not found during deserialization: "
                    + classNotFoundException.getMessage());
        }
        return null;
    }
}
