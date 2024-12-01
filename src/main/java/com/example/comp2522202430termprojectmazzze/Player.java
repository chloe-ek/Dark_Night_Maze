package com.example.comp2522202430termprojectmazzze;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import java.io.Serializable;
import java.io.Serial;

/**
 * Represents the player character in the Dark Night Maze game.
 * Handles player movement, item collection, flashlight control, and rendering.
 *
 * @author Eunji
 * @version 2024
 */
public class Player implements Character, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final String IMAGE_PATH_LEFT = "/images/redhoodie_left.png";
    private static final String IMAGE_PATH = "/images/redhoodie.png";
    private static final String DEAD_IMAGE_PATH = "/images/dead.png";

    private static final int FLASHLIGHT_RADIUS = 3;
    private static final double FLASHLIGHT_OPACITY = 0.5;
    private Position position;
    private transient Image playerImage;
    private transient Image deadImage;
    private Direction currentDirection;
    private int collectedItems = 0;
    private boolean isAlive = true;
    private final Flashlight flashlight;

    /**
     * Constructs a Player object at the specified starting position.
     *
     * @param startPosition the initial position of the player
     */
    public Player(final Position startPosition) {
        this.position = startPosition;
        this.currentDirection = Direction.RIGHT;
        this.flashlight = new Flashlight(this, FLASHLIGHT_RADIUS,
                Color.LIGHTYELLOW, FLASHLIGHT_OPACITY);
        loadImage(IMAGE_PATH);
        loadDeadImage();
    }

    /**
     * Restores transient fields after deserialization.
     *
     * @return the restored Player object
     */
    @Serial
    private Object readResolve() {
        loadImage(IMAGE_PATH);
        loadDeadImage();
        return this;
    }

    /**
     * Loads the player image based on the specified path.
     *
     * @param imagePath the path to the image file
     */
    private void loadImage(final String imagePath) {
        this.playerImage = ImageLoader.getInstance().loadImage(imagePath);
    }

    /**
     * Loads the image for the player's death state.
     */
    private void loadDeadImage() {
        this.deadImage = ImageLoader.getInstance().loadImage(DEAD_IMAGE_PATH);
    }

    /**
     * Checks if the player is alive.
     *
     * @return true if the player is alive, false otherwise
     */
    public boolean isAlive() {
        return !isAlive;
    }

    /**
     * Sets the player's state to dead.
     */
    public void die() {
        isAlive = false;
    }

    /**
     * Retrieves the current image of the player based on their state.
     *
     * @return the current player image
     */
    public Image getCurrentImage() {
        if (isAlive) {
            return playerImage;
        } else {
            return deadImage;
        }
    }

    /**
     * Increments the player's collected item count.
     */
    public void collectItem() {
        collectedItems++;
    }

    /**
     * Retrieves the number of items the player has collected.
     *
     * @return the number of collected items
     */
    public int getCollectedItems() {
        return collectedItems;
    }

    /**
     * Retrieves the flashlight object associated with the player.
     *
     * @return the player's Flashlight object
     */
    public Flashlight getFlashlight() {
        return flashlight;
    }

    /**
     * Retrieves the current position of the player.
     *
     * @return the Position object representing the player's position
     */
    @Override
    public Position getPosition() {
        return position;
    }

    /**
     * Moves the player in the specified direction if the move is valid.
     * Updates the player's image based on their direction.
     *
     * @param direction the Direction in which to move
     * @param maze      the maze structure represented as a 2D boolean array
     */
    @Override
    public void move(final Direction direction, final boolean[][] maze) {
        if (!isAlive) {
            return;
        }
        int newX = position.getCoordinateX() + direction.getDirectionX();
        int newY = position.getCoordinateY() + direction.getDirectionY();

        if (direction == Direction.LEFT && currentDirection != Direction.LEFT) {
            currentDirection = Direction.LEFT;
            loadImage(IMAGE_PATH_LEFT);
        } else if (direction == Direction.RIGHT && currentDirection != Direction.RIGHT) {
            currentDirection = Direction.RIGHT;
            loadImage(IMAGE_PATH);
        }
        if (isValidMove(newX, newY, maze)) {
            position = new Position(newX, newY);
        }
    }

    /**
     * Checks if the specified move is valid within the maze.
     *
     * @param targetX the target x-coordinate
     * @param targetY the target y-coordinate
     * @param maze    the maze structure represented as a 2D boolean array
     * @return true if the move is valid, false otherwise
     */
    private boolean isValidMove(final int targetX, final int targetY, final boolean[][] maze) {
        return targetX >= 0 && targetX < maze.length
                && targetY >= 0 && targetY < maze[0].length
                && !maze[targetX][targetY];
    }

    /**
     * Renders the player on the given GraphicsContext.
     *
     * @param gc       the GraphicsContext used for rendering
     * @param tileSize the size of each tile in pixels
     */
    @Override
    public void render(final GraphicsContext gc, final int tileSize) {
        Image imageToRender = getCurrentImage();
        if (imageToRender != null) {
            gc.drawImage(imageToRender, position.getCoordinateX() * tileSize,
                    position.getCoordinateY() * tileSize, tileSize, tileSize);
        } else {
            gc.setFill(Color.BLUE);
            gc.fillRect(position.getCoordinateX() * tileSize,
                    position.getCoordinateY() * tileSize, tileSize, tileSize);
        }
    }
}
