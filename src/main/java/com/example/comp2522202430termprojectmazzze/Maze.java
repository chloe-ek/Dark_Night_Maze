package com.example.comp2522202430termprojectmazzze;

import java.io.Serial;
import java.io.Serializable;
import java.util.Random;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * Represents the Maze structure for the Dark Night Maze game.
 * Handles maze generation, pathfinding, exit creation, and rendering.
 * Includes logic for random path creation and ensuring accessibility.
 *
 * @author Eunji
 * @version 2024
 */
public class Maze implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final int DIRECTIONS_COUNT = 4;
    private static final double RANDOM_PATH_THRESHOLD = 0.25;
    private static final int MIN_PATH_NEIGHBORS = 3;


    private boolean[][] structure;
    private final int width;
    private final int height;
    private final Random random = new Random();

    private transient Image wallImage;
    private transient Image tileImage;

    private Position exitPosition;

    /**
     * Constructs a Maze object with the given width and height.
     *
     * @param width  the width of the maze
     * @param height the height of the maze
     */
    Maze(final int width, final int height) {
        this.width = width;
        this.height = height;
        structure = new boolean[width][height];
        wallImage = ImageLoader.getInstance().loadImage("/images/wall.png");
        tileImage = ImageLoader.getInstance().loadImage("/images/tile.png");

    }

    /**
     * Restores transient fields after deserialization.
     *
     * @return the restored Maze object
     */
    @Serial
    private Object readResolve() {
        wallImage = ImageLoader.getInstance().loadImage("/images/wall.png");
        tileImage = ImageLoader.getInstance().loadImage("/images/tile.png");
        return this;
    }

    /**
     * Generates the maze structure by initializing walls, paths, and creating an exit.
     */
    public void generateMaze() {
        // Initialize all cells as walls
        structure = new boolean[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                structure[x][y] = true;
            }
        }

        setBorderWalls();
        generatePath(1, 1);
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

    /**
     * Sets the outer border of the maze as walls.
     */
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

    /**
     * Recursively generates a path from the given starting point.
     *
     * @param x the starting x-coordinate
     * @param y the starting y-coordinate
     */
    private void generatePath(final int x, final int y) {
        structure[x][y] = false; // Mark current cell as path

        int[] directions = {0, 1, 2, MIN_PATH_NEIGHBORS};
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
            } else if (direction == MIN_PATH_NEIGHBORS) {
                newX -= 1; // LEFT
            }

            // Check if we can move in this direction
            if (isValidPathCell(newX, newY)) {
                structure[newX][newY] = false;
                generatePath(newX, newY);
            }
        }
    }

    /**
     * Checks if a cell is valid for path creation.
     *
     * @param x the x-coordinate of the cell
     * @param y the y-coordinate of the cell
     * @return true if the cell is valid, false otherwise
     */
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
        return adjacentPaths == 2 && random.nextDouble() < RANDOM_PATH_THRESHOLD;
    }

    /**
     * Adds an exit to the maze at a random edge.
     */
    private void addExit() {
        Position exit = generateExitPosition();
        structure[exit.getCoordinateX()][exit.getCoordinateY()] = false;
        exitPosition = exit;
        ensureExitAccessibility(exit);
    }

    /**
     * Generates a random exit position at one of the maze edges.
     *
     * @return the position of the exit
     */
    private Position generateExitPosition() {
        int edge = random.nextInt(DIRECTIONS_COUNT);
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

        return new Position(exitX, exitY);
    }

    /**
     * Ensures the exit is accessible by creating a path around it.
     *
     * @param exit the position of the exit
     */
    private void ensureExitAccessibility(final Position exit) {
        int exitX = exit.getCoordinateX();
        int exitY = exit.getCoordinateY();

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

    /**
     * Checks if the specified position is valid for an exit.
     *
     * @param x the x-coordinate of the position
     * @param y the y-coordinate of the position
     * @return true if the position is valid, false otherwise
     */
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

    /**
     * Retrieves the position of the exit.
     *
     * @return the Position object representing the exit
     */
    public Position getExitPosition() {
        return exitPosition;
    }

    /**
     * Shuffles an array of directions.
     *
     * @param array the array to shuffle
     */
    private void shuffleArray(final int[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    /**
     * Retrieves the maze structure as a 2D boolean array.
     *
     * @return the maze structure
     */
    public boolean[][] getStructure() {
        return structure;
    }

    /**
     * Retrieves a random free position within the maze.
     *
     * @return the Position object of a random free cell
     */
    public Position getRandomFreePosition() {
        int x;
        int y;
        do {
            x = 1 + random.nextInt(width - 2);
            y = 1 + random.nextInt(height - 2);
        } while (structure[x][y]);
        return new Position(x, y);
    }

    /**
     * Renders the maze structure on a canvas.
     *
     * @param gc       the GraphicsContext used to draw the maze
     * @param tileSize the size of each maze tile
     */
    public void render(final GraphicsContext gc, final int tileSize) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (structure[x][y]) {
                    gc.drawImage(wallImage, x * tileSize, y * tileSize, tileSize, tileSize);
                } else {
                    gc.drawImage(tileImage, x * tileSize, y * tileSize, tileSize, tileSize);
                }
            }
        }
        if (exitPosition != null) {
            gc.setFill(Color.BEIGE);
            gc.fillRect(exitPosition.getCoordinateX() * tileSize,
                    exitPosition.getCoordinateY() * tileSize, tileSize, tileSize);
        }
    }

}
