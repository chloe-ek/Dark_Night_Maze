package com.example.comp2522202430termprojectmazzze;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Manages the main game screen, including game logic, rendering, and user input.
 * <p>
 * This class initializes the game environment, handles input events, and updates
 * the game state in real-time using an AnimationTimer.
 *
 * @author Eunji
 * @version 2024
 */
public class GameController {

    private static final String SAVE_FILE = System.getProperty("user.dir") + "/game_save.dat";

    private static final int TILE_SIZE = 40;
    private static final int WIDTH = 25;
    private static final int HEIGHT = 20;

    private GameLogic gameLogic;
    private GameRenderer gameRenderer;

    /**
     * Displays the game screen and sets up the game environment.
     *
     * @param primaryStage the primary stage where the game screen is displayed
     */
    public void showGameScreen(final Stage primaryStage) {
        primaryStage.setTitle("Dark Night Maze");

        Canvas canvas = setupCanvas();
        GraphicsContext gc = canvas.getGraphicsContext2D();

        initializeGameLogic();
        setupScene(canvas, primaryStage);

        startGameLoop(gc);
    }

    /**
     * Sets up the canvas for rendering.
     *
     * @return the initialized canvas
     */
    private Canvas setupCanvas() {
        return new Canvas(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
    }

    /**
     * Initializes the game logic and renderer.
     */
    private void initializeGameLogic() {
        gameLogic = new GameLogic(WIDTH, HEIGHT);
        gameRenderer = new GameRenderer();
    }

    /**
     * Sets up the scene and handles input events.
     *
     * @param canvas the canvas to be added to the scene
     * @param stage the primary stage for the game
     */
    private void setupScene(final Canvas canvas, final Stage stage) {
        Scene scene = new Scene(new StackPane(canvas));
        stage.setScene(scene);
        stage.show();

        scene.setOnKeyPressed(this::handleKeyPress);
    }

    /**
     * Handles key press events for saving, loading, and other game controls.
     *
     * @param event the key event containing the pressed key
     */
    private void handleKeyPress(final KeyEvent event) {
        switch (event.getCode()) {
            case S -> GameSaver.saveGame(gameLogic, SAVE_FILE);
            case L -> loadGame();
            default -> gameLogic.handleInput(event);
        }
    }

    /**
     * Loads a previously saved game state and updates the game environment.
     */
    private void loadGame() {
        GameLogic loadedGame = GameSaver.loadGame(SAVE_FILE);
        if (loadedGame != null) {
            gameLogic = loadedGame;
            System.out.println("Game Loaded!");
        } else {
            System.out.println("Failed to load game.");
        }
    }

    /**
     * Starts the game update loop using an AnimationTimer.
     *
     * @param gc the GraphicsContext used for rendering
     */
    private void startGameLoop(final GraphicsContext gc) {
        new AnimationTimer() {
            @Override
            public void handle(final long now) {
                gameLogic.update();
                gameRenderer.render(gc, gameLogic, TILE_SIZE);
            }
        }.start();
    }
}
