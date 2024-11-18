package com.example.comp2522202430termprojectmazzze;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;


public class GameRenderer {
    public static void drawEntity(final GraphicsContext gc, final Position position, final int tileSize, final Color color) {
        gc.setFill(color);
        gc.fillRect(position.getX() * tileSize, position.getY() * tileSize, tileSize, tileSize);
    }
    public static void renderMaze(final GraphicsContext gc, final Maze maze, final int tileSize) {
        boolean[][] structure = maze.getStructure();
        for (int x = 0; x < structure.length; x++) {
            for (int y = 0; y < structure[0].length; y++) {
                gc.setFill(structure[x][y] ? Color.GRAY : Color.BLACK);
                gc.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
            }
        }
    }

    public static void renderPlayer(final GraphicsContext gc, final Player player, final int tileSize, final Flashlight flashlight) {
        flashlight.draw(gc, tileSize);
        drawEntity(gc, player.getPosition(), tileSize, Color.BLUE);
    }


    public static void renderGhosts(final GraphicsContext gc, final List<Ghost> ghosts, final int tileSize) {
        for (Ghost ghost : ghosts) {
            drawEntity(gc, ghost.getPosition(), tileSize, Color.WHITE);
        }
    }

    public static void renderItems(final GraphicsContext gc, final List<Item> items, final int tileSize) {
        gc.setFill(Color.GREEN);
        for (Item item : items) {
            Position position = item.getPosition();
            gc.fillRect(position.getX() * tileSize + tileSize / 4,
                    position.getY() * tileSize + tileSize / 4, tileSize / 2, tileSize / 2);
        }
    }
}
