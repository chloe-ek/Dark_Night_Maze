package com.example.comp2522202430termprojectmazzze;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class GameController extends Application {
    private static final int TILE_SIZE = 40;
    private static final int WIDTH = 20;
    private static final int HEIGHT = 20;

    private Player player;
    private Flashlight flashlight;
    private Maze maze;
    private List<Ghost> ghosts;
    private List<Item> items;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Dark Night Maze"); //window title
        Canvas canvas = new Canvas(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        //initialize
        initializeGame();

        Scene scene = new Scene(new StackPane(canvas));

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP -> player.move(0, -1, maze.getStructure());
                case DOWN -> player.move(0, 1, maze.getStructure());
                case LEFT -> player.move(-1, 0, maze.getStructure());
                case RIGHT -> player.move(1, 0, maze.getStructure());
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateGame();
                render(gc);
            }
        }.start();
    }

    private void initializeGame() {
        maze = new Maze(WIDTH, HEIGHT);
        player = new Player(1, 1);
        flashlight = new Flashlight(3, javafx.scene.paint.Color.YELLOW, 0.5);
        ghosts = new ArrayList<>();
        items = new ArrayList<>();

        maze.generateMaze();
        generateGhosts(5);
        generateItems(3);
    }

    private void updateGame() {
        for (Ghost ghost : ghosts) {
            ghost.randomMove(maze.getStructure());
        }
    }

    private void render(GraphicsContext gc) {
        GameRenderer.renderMaze(gc, maze, TILE_SIZE);
        GameRenderer.renderPlayer(gc, player, TILE_SIZE, flashlight);
        GameRenderer.renderGhosts(gc, ghosts, TILE_SIZE);
        GameRenderer.renderItems(gc, items, TILE_SIZE);
    }

    private void generateGhosts(int count) {
        for (int i = 0; i < count; i++) {
            ghosts.add(new Ghost(maze.getRandomFreePosition(), 500 + (long) (Math.random() * 1000)));
        }
    }

    private void generateItems(int count) {
        for (int i = 0; i < count; i++) {
            items.add(new Item(maze.getRandomFreePosition()));
        }
    }
}




