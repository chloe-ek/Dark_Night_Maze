package com.example.comp2522202430termprojectmazzze;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import java.util.ArrayList;
import java.util.Random;

public class DarkNightMaze extends Application {
    private static final int TILE_SIZE = 40;
    private static final int WIDTH = 20;
    private static final int HEIGHT = 20;

    private Player player;
    private Flashlight flashlight;
    private ArrayList<Ghost> ghosts = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private boolean[][] maze; //Maze structure
    private Image playerImage;
    private Random random = new Random();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Dark Night Maze"); //window title
        Canvas canvas = new Canvas(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        //initialize
        generateMaze();
        generateGhosts(5);
        generateItems(3);
        player = new Player(1, 1); // set player start position
        flashlight = new Flashlight(3, Color.YELLOW, 0.5);

        // image load
        try {
            playerImage = new Image(getClass().getResource("/images/ghost.png").toExternalForm());
        } catch (NullPointerException e) {
            System.out.println("failed to load image");
            playerImage = null;
        }

        // set up scene and input handling
        Scene scene = new Scene(new StackPane(canvas));
        scene.setOnKeyPressed(this::handleKeyPress);

        primaryStage.setScene(scene);
        primaryStage.show();

        // Animation loop fo rendering and game updates
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                drawMaze(gc);
                drawPlayer(gc);
                drawGhosts(gc);
                drawItems(gc);
                for (Ghost ghost : ghosts) {
                    ghost.randomMove(maze); // Ghost 랜덤 이동
                }
            }
        }.start();
    }

    private void drawPlayer(GraphicsContext gc) {
        if (playerImage != null) {
            gc.drawImage(playerImage, player.getX() * TILE_SIZE, player.getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        } else {
            gc.setFill(Color.BLUE); // Default color if image fails
            gc.fillRect(player.getX() * TILE_SIZE, player.getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }

        // flashlight setting and draw
        flashlight.setPosition(player.getX(), player.getY());
        flashlight.draw(gc, TILE_SIZE);
    }


    private void generateMaze() {
        maze = new boolean[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                maze[x][y] = true; // Initialize all tiles as walls
            }
        }
        // Create a path from start to end using a simple algorithm
        int x = 1;
        int y = 1;
        maze[x][y] = false; // Start point
        while (x < WIDTH - 2 || y < HEIGHT - 2) {
            if (x < WIDTH - 2 && random.nextBoolean()) {
                x++;
            } else if (y < HEIGHT - 2) {
                y++;
            }
            maze[x][y] = false; // Mark as a path
        }
        maze[WIDTH - 2][HEIGHT - 2] = false; // End point
    }

    private void generateGhosts(int count) {
        for (int i = 0; i < count; i++) {
            int x = random.nextInt(WIDTH);
            int y = random.nextInt(HEIGHT);
            long moveInterval = 500 + random.nextInt(1000);// Random move interval (500-1500ms)
            String imagePath = "/images/ghost.png";
            ghosts.add(new Ghost(x, y, moveInterval,imagePath));
        }
    }

    private void generateItems(int count) {
        for (int i = 0; i < count; i++) {
            int x = random.nextInt(WIDTH);
            int y = random.nextInt(HEIGHT);
            if (!maze[x][y] && (x != 1 || y != 1)) {
                items.add(new Item(x, y));
            }
        }
    }

    private void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case UP -> player.move(0, -1, maze);
            case DOWN -> player.move(0, 1, maze);
            case LEFT -> player.move(-1, 0, maze);
            case RIGHT -> player.move(1, 0, maze);
        }
    }

    // Draw the maze based on tile state (wall or path)
    private void drawMaze(final GraphicsContext gc) {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                gc.setFill(maze[x][y] ? Color.GRAY : Color.BLACK);
                gc.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
        gc.setFill(Color.RED);
        gc.fillRect((WIDTH - 2) * TILE_SIZE, (HEIGHT - 2) * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }

    // Draw ghost characters
    private void drawGhosts(final GraphicsContext gc) {
        for (Ghost ghost : ghosts) {
            if (ghost.getGhostImage() != null ){
                gc.drawImage(ghost.getGhostImage(), ghost.getX() * TILE_SIZE, ghost.getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            } else {
                gc.setFill(Color.WHITE); // if image fails to load
                gc.fillOval(ghost.getX() * TILE_SIZE, ghost.getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE);


            }
        }
    }

    // Draw collectible items
    private void drawItems(final GraphicsContext gc) {
        gc.setFill(Color.GREEN);
        for (Item item : items) {
            gc.fillRect(item.getX() * TILE_SIZE + TILE_SIZE / 4, item.getY() * TILE_SIZE + TILE_SIZE / 4, TILE_SIZE / 2, TILE_SIZE / 2);
        }
    }

}

