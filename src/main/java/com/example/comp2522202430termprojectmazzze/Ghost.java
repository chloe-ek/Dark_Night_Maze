package com.example.comp2522202430termprojectmazzze;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import java.io.Serializable;
import java.util.Random;

/**
 * Represents a ghost character in the game that can move, render itself, and track the player.
 * Ghosts either move randomly or chase the player if within a certain detection radius.
 *
 * @author Eunji
 * @version 2024
 */
public class Ghost implements Character, Updatable, Serializable {
    private static final String IMAGE_PATH = "/images/ghost.png";
    private static final Image GHOST_IMAGE = ImageLoader.getInstance().loadImage(IMAGE_PATH);

    private static final int RANDOM_MOVE = 3;
    private Position position;
    private final Random random = new Random();
    private long lastMoveTime = 0;
    private final long moveInterval;
    private final GameLogic gameLogic;

    /**
     * Constructs a ghost with the specified starting position, move interval,
     * and game logic reference.
     *
     * @param startPosition the starting position of the ghost as a Position
     * @param moveInterval the interval between moves in milliseconds as a long
     * @param gameLogic the game logic managing the ghost as a GameLogic
     */
    public Ghost(final Position startPosition, final long moveInterval, final GameLogic gameLogic) {
        this.position = startPosition;
        this.moveInterval = moveInterval;
        this.gameLogic = gameLogic;
    }

    /**
     * Gets the current position of the ghost.
     *
     * @return the current position of the ghost as a Position
     */
    @Override
    public Position getPosition() {
        return position;
    }

    /**
     * Updates the ghost's behavior, either moving randomly or chasing the player.
     *
     * @param maze a 2D boolean array representing the maze structure
     */
    @Override
    public void update(final boolean[][] maze) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastMoveTime >= moveInterval) {
            double distanceToPlayer = position.distanceTo(gameLogic.getPlayerPosition());

            if (distanceToPlayer <= GameLogic.getGhostDetectionRadius()) {
                moveTowardPlayer(maze, gameLogic.getPlayerPosition());
            } else {
                Direction randomDirection = getRandomDirection();
                move(randomDirection, maze);
            }
            lastMoveTime = currentTime;
        }
    }

    /**
     * Moves the ghost toward the player's position.
     *
     * @param maze a 2D boolean array representing the maze structure
     * @param playerPosition the player's position as a Position
     */
    private void moveTowardPlayer(final boolean[][] maze, final Position playerPosition) {
        int dx = playerPosition.getCoordinateX() - position.getCoordinateX();
        int dy = playerPosition.getCoordinateY() - position.getCoordinateY();

        Direction direction = null;

        if (Math.abs(dx) > Math.abs(dy)) {
            if (dx > 0) {
                direction = Direction.RIGHT;
            } else {
                direction = Direction.LEFT;
            }
        } else if (dy != 0) {
            if (dy > 0) {
                direction = Direction.DOWN;
            } else {
                direction = Direction.UP;
            }
        }

        if (direction != null) {
            move(direction, maze);
        }
    }

    /**
     * Moves the ghost in a specified direction if the move is valid within the maze.
     *
     * @param direction the direction to move in as a Direction
     * @param maze a 2D boolean array representing the maze structure
     */
    @Override
    public void move(final Direction direction, final boolean[][] maze) {
        int newX = position.getCoordinateX() + direction.getDirectionX();
        int newY = position.getCoordinateY() + direction.getDirectionY();


        if (newX >= 0 && newX < maze.length && newY >= 0 && newY < maze[0].length) {
            position = new Position(newX, newY);
        }
    }

    /**
     * Selects a random direction for the ghost to move in.
     *
     * @return a randomly chosen Direction
     */
    private Direction getRandomDirection() {
        int dx = random.nextInt(RANDOM_MOVE) - 1;
        int dy = random.nextInt(RANDOM_MOVE) - 1;

        for (Direction direction : Direction.values()) {
            if (direction.getDirectionX() == dx && direction.getDirectionY() == dy) {
                return direction;
            }
        }
        return Direction.UP;
    }

    /**
     * Renders the ghost on the canvas.
     *
     * @param gc the GraphicsContext used for rendering
     * @param tileSize the size of each tile in pixels as an int
     */
    @Override
    public void render(final GraphicsContext gc, final int tileSize) {
        if (GHOST_IMAGE != null) {
            gc.drawImage(GHOST_IMAGE, position.getCoordinateX() * tileSize,
                    position.getCoordinateY() * tileSize, tileSize, tileSize);
        } else {
            gc.setFill(Color.WHITE);
            gc.fillOval(position.getCoordinateX() * tileSize,
                    position.getCoordinateY() * tileSize, tileSize, tileSize);
        }
    }
}
