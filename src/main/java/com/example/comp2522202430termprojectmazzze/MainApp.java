package com.example.comp2522202430termprojectmazzze;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Entry point for the Dark Night Maze game.
 * This class initializes the JavaFX application and displays the introduction screen.
 * It serves as the starting point of the game.
 *
 * @author Eunji
 * @version 2024
 */
public class MainApp extends Application {

    /**
     * Starts the JavaFX application by displaying the introduction screen.
     *
     * @param primaryStage the primary stage for the JavaFX application
     */
    @Override
    public void start(final Stage primaryStage) {

        IntroScreen introScreen = new IntroScreen(primaryStage);
        introScreen.showIntro();
    }

    /**
     * Launches the JavaFX application.
     *
     * @param args command-line arguments passed to the application
     */
    public static void main(final String[] args) {
        launch(args);
    }
}
