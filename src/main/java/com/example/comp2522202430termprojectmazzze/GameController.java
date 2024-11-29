package com.example.comp2522202430termprojectmazzze;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;


public class GameController extends Application {
    private static final String SAVE_FILE = System.getProperty("user.dir") + "/game_save.dat";

    private static final int TILE_SIZE = 40;
    private static final int WIDTH = 25;
    private static final int HEIGHT = 20;

    private GameLogic gameLogic;
    private GameRenderer gameRenderer;
    private Flashlight flashlight;



    @Override
    public void start(final Stage primaryStage) {
        primaryStage.setTitle("Dark Night Maze"); //window title

        Canvas canvas = new Canvas(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Initialize game logic and renderer

        gameLogic = new GameLogic(WIDTH, HEIGHT);
        gameRenderer = new GameRenderer();

        // Initialize flashlight
        Player player = gameLogic.getPlayer();
        flashlight = new Flashlight(player, 3, Color.LIGHTYELLOW, 0.5);


        Scene scene = new Scene(new StackPane(canvas));


        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.S) {
                GameSaver.saveGame(gameLogic, SAVE_FILE);
            } else if (event.getCode() == KeyCode.L) {
                GameLogic loadedGame = GameSaver.loadGame(SAVE_FILE);
                if (loadedGame != null) {
                    gameLogic = loadedGame;
                    flashlight = new Flashlight(gameLogic.getPlayer(), 3, Color.LIGHTYELLOW, 0.5);
                    System.out.println("Game Loaded!");
                } else {
                    System.out.println("Failed to load game.");
                }
            } else {
                gameLogic.handleInput(event);
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();

        new AnimationTimer() {
            @Override
            public void handle(final long now) {
                gameLogic.update();
                gameRenderer.render(gc, gameLogic, TILE_SIZE);
            }
        }.start();
    }

    private void showTemporaryMessage(final GraphicsContext gc, final String message) {
        gc.setFill(Color.WHITE);
        gc.fillText(message, 10, 20); // 메시지 위치: (10, 20)

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    gc.clearRect(10, 5, 150, 30); // 메시지가 그려진 영역만 지움
                });
            }
        }, 2000); // 2초 후 메시지 지움
    }

}
