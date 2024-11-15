package com.example.comp2522202430termprojectmazzze;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;


public class GameRenderer {
    public static void renderMaze(GraphicsContext gc, Maze maze, int tileSize) {
        boolean[][] structure = maze.getStructure();
        for (int x = 0; x < structure.length; x++) {
            for (int y = 0; y < structure[0].length; y++) {
                gc.setFill(structure[x][y] ? Color.GRAY : Color.BLACK);
                gc.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
            }
        }
        gc.setFill(Color.RED);
        gc.fillRect((structure.length - 2) * tileSize, (structure[0].length - 2) * tileSize, tileSize, tileSize);
    }

    public static void renderPlayer(GraphicsContext gc, Player player, int tileSize, Flashlight flashlight) {
        flashlight.setPosition(player.getX(), player.getY());
        flashlight.draw(gc, tileSize);
        gc.setFill(Color.BLUE);
        gc.fillRect(player.getX() * tileSize, player.getY() * tileSize, tileSize, tileSize);
    }

    public static void renderGhosts(GraphicsContext gc, List<Ghost> ghosts, int tileSize) {
        for (Ghost ghost : ghosts) {
            gc.setFill(Color.WHITE);
            gc.fillOval(ghost.getX() * tileSize, ghost.getY() * tileSize, tileSize, tileSize);
        }
    }

    public static void renderItems(GraphicsContext gc, List<Item> items, int tileSize) {
        gc.setFill(Color.GREEN);
        for (Item item : items) {
            gc.fillRect(item.getX() * tileSize + tileSize / 4, item.getY() * tileSize + tileSize / 4, tileSize / 2, tileSize / 2);
        }
    }
}
