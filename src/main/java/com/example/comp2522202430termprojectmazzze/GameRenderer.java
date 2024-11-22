package com.example.comp2522202430termprojectmazzze;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class GameRenderer {

    public void render(final GraphicsContext gc, final GameLogic gameLogic, int tileSize) {
        renderMaze(gc, gameLogic.getMaze(), tileSize);
        // render the character
        for (Character character : gameLogic.getCharacters()) {
            character.render(gc, tileSize);
        }
        // render the items
        for (Item item : gameLogic.getItems()) {
            item.render(gc, tileSize);
        }
    }

    private void renderMaze(final GraphicsContext gc, final Maze maze, final int tileSize) {
        boolean[][] structure = maze.getStructure();
        for (int x = 0; x < structure.length; x++) {
            for (int y = 0; y < structure[0].length; y++) {
                if (structure[x][y]) {
                    gc.setFill(Color.DARKGREY);
                } else {
                    gc.setFill(javafx.scene.paint.Color.BLACK);
                }
                gc.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
            }
        }
    }

}
