package com.example.comp2522202430termprojectmazzze;

import java.util.Random;

public class Maze {
    private boolean[][] structure;
    private final int width;
    private final int height;
    private final Random random = new Random();

    public Maze(int width, int height) {
        this.width = width;
        this.height = height;
        structure = new boolean[width][height];
    }

    public void generateMaze() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                structure[x][y] = true; // Initialize as walls
            }
        }
        int x = 1, y = 1;
        structure[x][y] = false; // Start point

        while (x < width - 2 || y < height - 2) {
            if (x < width - 2 && random.nextBoolean()) {
                x++;
            } else if (y < height - 2) {
                y++;
            }
            structure[x][y] = false; // Mark as a path
        }
        structure[width - 2][height - 2] = false; // End point
    }

    public boolean[][] getStructure() {
        return structure;
    }

    public Position getRandomFreePosition() {
        int x;
        int y;
        do {
            x = random.nextInt(width);
            y = random.nextInt(height);
        } while (structure[x][y]);
        return new Position(x, y);
    }
}
