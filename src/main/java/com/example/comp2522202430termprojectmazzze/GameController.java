package com.example.comp2522202430termprojectmazzze;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class GameController extends Application {

    private static final int TILE_SIZE = 40;
    private static final int WIDTH = 20;
    private static final int HEIGHT = 20;

    private GameLogic gameLogic;
    private GameRenderer gameRenderer;

    @Override
    public void start(final Stage primaryStage) {
        primaryStage.setTitle("Dark Night Maze"); //window title

        Canvas canvas = new Canvas(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gameLogic = new GameLogic(WIDTH, HEIGHT);
        gameRenderer = new GameRenderer();

        Scene scene = new Scene(new StackPane(canvas));
        scene.setOnKeyPressed(gameLogic::handleInput);

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
}