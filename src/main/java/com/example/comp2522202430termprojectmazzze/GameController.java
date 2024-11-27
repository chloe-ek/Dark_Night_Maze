package com.example.comp2522202430termprojectmazzze;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class GameController extends Application {
    private static final String SAVE_FILE = "game_save.dat";

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
        scene.setOnKeyPressed(gameLogic::handleInput);

        primaryStage.setScene(scene);
        primaryStage.show();

        new AnimationTimer() {
            @Override
            public void handle(final long now) {
                gameLogic.update(); // Update game logic
                gameRenderer.render(gc, gameLogic, TILE_SIZE, flashlight);
            }
        }.start();
    }
}