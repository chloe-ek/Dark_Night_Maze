package com.example.comp2522202430termprojectmazzze;

import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


class Maze {
    private boolean[][] structure;
    private final int width;
    private final int height;
    private final Random random = new Random();

    private final Image wallImage;
    private final Image tileImage;

    Maze(final int width, final int height) {
        this.width = width;
        this.height = height;
        structure = new boolean[width][height];

        wallImage = new Image(getClass().getResourceAsStream("/images/wall.png"));
        tileImage = new Image(getClass().getResourceAsStream("/images/tile.png"));
    }

    public void generateMaze() {
        // Initialize all cells as walls
        structure = new boolean[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                structure[x][y] = true;
            }
        }

        setBorderWalls();
        // Generate paths starting from the center
        generatePath(1, 1);

        // Add exit
        addExit();

        // Ensure start position is accessible
        int centerX = width / 2;
        int centerY = height / 2;
        structure[centerX][centerY] = false;
        // Make sure there's a path to adjacent cells
        if (centerX > 1) {
            structure[centerX - 1][centerY] = false;
        }
        if (centerX < width - 2) {
            structure[centerX + 1][centerY] = false;
        }
        if (centerY > 1) {
            structure[centerX][centerY - 1] = false;
        }
        if (centerY < height - 2) {
            structure[centerX][centerY + 1] = false;
        }
    }

    private void setBorderWalls() {

        for (int x = 0; x < width; x++) {
            structure[x][0] = true;          // Top wall
            structure[x][height - 1] = true;   // Bottom wall
        }
        for (int y = 0; y < height; y++) {
            structure[0][y] = true;          // Left wall
            structure[width - 1][y] = true;    // Right wall
        }
    }

    private void generatePath(final int x, final int y) {
        structure[x][y] = false; // Mark current cell as path

        int[] directions = {0, 1, 2, 3};
        shuffleArray(directions);

        for (int direction : directions) {
            int newX = x;
            int newY = y;

            if (direction == 0) {
                newY -= 1; // UP
            } else if (direction == 1) {
                newX += 1; // RIGHT
            } else if (direction == 2) {
                newY += 1; // DOWN
            } else if (direction == 3) {
                newX -= 1; // LEFT
            }

            // Check if we can move in this direction
            if (isValidPathCell(newX, newY)) {
                structure[newX][newY] = false;
                generatePath(newX, newY);
            }
        }
    }

    private boolean isValidPathCell(final int x, final int y) {
        // Check boundaries (leaving space for walls)
        if (x <= 0 || y <= 0 || x >= width - 1 || y >= height - 1) {
            return false;
        }

        // Count number of adjacent paths to avoid too many connections
        int adjacentPaths = 0;
        if (!structure[x - 1][y]) {
            adjacentPaths++;
        }
        if (!structure[x + 1][y]) {
            adjacentPaths++;
        }
        if (!structure[x][y - 1]) {
            adjacentPaths++;
        }
        if (!structure[x][y + 1]) {
            adjacentPaths++;
        }

        if (adjacentPaths <= 1) {
            return true;
        }
        return adjacentPaths == 2 && random.nextDouble() < 0.25;
    }

    private void addExit() {
        int edge = random.nextInt(4);
        int exitX;
        int exitY;

        do {
            switch (edge) {
                case 0 -> { // Top edge
                    exitX = 1 + random.nextInt(width - 2);
                    exitY = 0;
                }
                case 1 -> { // Bottom edge
                    exitX = 1 + random.nextInt(width - 2);
                    exitY = height - 1;
                }
                case 2 -> { // Left edge
                    exitX = 0;
                    exitY = 1 + random.nextInt(height - 2);
                }
                default -> { // Right edge
                    exitX = width - 1;
                    exitY = 1 + random.nextInt(height - 2);
                }
            }
        } while (!isValidExit(exitX, exitY));

        structure[exitX][exitY] = false;
        if (exitX == 0) {
            structure[1][exitY] = false;
        } else if (exitX == width - 1) {
            structure[width - 2][exitY] = false;
        } else if (exitY == 0) {
            structure[exitX][1] = false;
        } else if (exitY == height - 1) {
            structure[exitX][height - 2] = false;
        }
    }

    private boolean isValidExit(final int x, final int y) {
        // Check if there's a potential path near the exit
        if (x == 0) {
            return !structure[1][y];
        }
        if (x == width - 1) {
            return !structure[width - 2][y];
        }
        if (y == 0) {
            return !structure[x][1];
        }
        if (y == height - 1) {
            return !structure[x][height - 2];
        }
        return false;
    }

    private void shuffleArray(final int[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    public boolean[][] getStructure() {
        return structure;
    }

    public Position getRandomFreePosition() {
        int x;
        int y;
        do {
            x = 1 + random.nextInt(width - 2);
            y = 1 + random.nextInt(height - 2);
        } while (structure[x][y]);
        return new Position(x, y);
    }

    public void render(final GraphicsContext gc, final int tileSize) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (structure[x][y]) {
                    // 벽일 경우
                    gc.drawImage(wallImage, x * tileSize, y * tileSize, tileSize, tileSize);
                } else {
                    // 길일 경우
                    gc.drawImage(tileImage, x * tileSize, y * tileSize, tileSize, tileSize);
                }
            }
        }
    }
}
