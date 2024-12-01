package com.example.comp2522202430termprojectmazzze;

import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.io.Serial;

/**
 * Handles the core game logic, including player actions, character updates,
 * and game state management.
 * <p>
 * This class manages the maze structure, player and character positions, items,
 * and the overall game state, including win and lose conditions. It also integrates
 * with a sound manager for playing sound effects based on game events.
 *
 * @author Eunji
 * @version 2024
 */
public class GameLogic implements Serializable {
    private static final double GHOST_DETECTION_RADIUS = 3.0;
    private static final int GHOST_MOVE_INTERVAL = 1000;
    private static final int ITEM_COUNT = 5;
    private static final int GHOST_COUNT = 5;
    private static final int FULL_MAP_VISIBILITY_DURATION = 2000;

    private transient Image itemImage = ImageLoader.getInstance().
            loadImage("/images/item.png");

    private final Maze maze;
    private final Player player;
    private final List<Character> characters;
    private final List<Item> items;
    private boolean isFullMapVisible = false;
    private boolean isGameWon = false;
    private transient SoundManager soundManager;

    /**
     * Constructs a new GameLogic instance with the specified maze dimensions.
     *
     * @param width the width of the maze as an int
     * @param height the height of the maze as an int
     */
    public GameLogic(final int width, final int height) {
        maze = new Maze(width, height);
        maze.generateMaze();

        player = new Player(new Position(width / 2, height / 2));
        characters = new ArrayList<>();
        characters.add(player);
        for (int i = 0; i < GHOST_COUNT; i++) {
            characters.add(new Ghost(maze.getRandomFreePosition(),
                    GHOST_MOVE_INTERVAL, this));
        }
        items = new ArrayList<>();
        for (int i = 0; i < ITEM_COUNT; i++) {
            items.add(new Item(maze.getRandomFreePosition()));
        }
        soundManager = new SoundManager();
    }

    @Serial
    private Object readResolve() {
        soundManager = new SoundManager();
        itemImage = ImageLoader.getInstance().loadImage("/images/item.png");
        return this;
    }
    /**
     * Retrieves the list of items in the game.
     *
     * @return a List of Item objects
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * Retrieves the image used for rendering items.
     *
     * @return an Image object representing the item image
     */
    public Image getItemImage() {
        return itemImage;
    }

    /**
     * Updates the game state, including player and ghost interactions, item collection,
     * and game win/lose conditions.
     */
    public void update() {
        if (isGameEndCondition()) {
            return;
        }

        if (checkWinCondition()) {
            return;
        }

        boolean ghostNearby = updateCharactersAndDetectGhosts();
        handleGhostSound(ghostNearby);

        processItemCollection();
        checkPlayerCollisionWithGhost();
    }

    /**
     * Checks if the game is over due to the player dying or the game being won.
     *
     * @return true if the game has ended, false otherwise
     */
    private boolean isGameEndCondition() {
        return player.isAlive() || isGameWon;
    }

    /**
     * Checks if the player has reached the maze exit, marking the game as won if true.
     *
     * @return true if the player has won, false otherwise
     */
    private boolean checkWinCondition() {
        if (player.getPosition().equals(maze.getExitPosition())) {
            isGameWon = true;
            handleGameWin();
            return true;
        }
        return false;
    }

    /**
     * Updates all characters in the game and checks if any ghost is near the player.
     *
     * @return true if a ghost is near the player, false otherwise
     */
    private boolean updateCharactersAndDetectGhosts() {
        boolean ghostNearby = false;
        for (Character character : characters) {
            if (character instanceof Ghost ghost) {
                ghost.update(maze.getStructure());
                double distance = player.getPosition().distanceTo(character.getPosition());
                if (distance <= GHOST_DETECTION_RADIUS) {
                    ghostNearby = true;
                }
            }
        }
        return ghostNearby;
    }

    /**
     * Handles the ghost sound based on whether a ghost is near the player.
     *
     * @param ghostNearby true if a ghost is near the player, false otherwise
     */
    private void handleGhostSound(final boolean ghostNearby) {
        if (soundManager != null) {
            if (ghostNearby) {
                soundManager.playGhostSound();
            } else {
                soundManager.stopGhostSound();
            }
        } else {
            System.err.println("Warning: SoundManager is null in update().");
        }
    }

    /**
     * Processes item collection by the player and updates visibility if necessary.
     */
    private void processItemCollection() {
        items.removeIf(item -> {
            if (player.getPosition().equals(item.getPosition())) {
                player.collectItem();
                checkFullMapVisibility();
                return true;
            }
            return false;
        });
    }

    /**
     * Checks if the player collides with any ghost and handles the player's death if true.
     */
    private void checkPlayerCollisionWithGhost() {
        for (Character character : characters) {
            if (character instanceof Ghost && player.getPosition().
                    equals(character.getPosition())) {
                player.die();
                handlePlayerDeath();
                break;
            }
        }
    }

    /**
     * Handles player input by interpreting key events and moving the player accordingly.
     *
     * @param event a KeyEvent representing the user's input
     */
    public void handleInput(final KeyEvent event) {
        Direction direction = Direction.fromKeyCode(event.getCode());
        if (direction != null) {
            player.move(direction, maze.getStructure());
        }
    }

    /**
     * Retrieves the ghost detection radius.
     *
     * @return the ghost detection radius as a double
     */
    public static double getGhostDetectionRadius() {
        return GHOST_DETECTION_RADIUS;
    }

    private void handleGameWin() {
        System.out.println("You Win! The player has reached the exit.");
    }

    private void handlePlayerDeath() {
        System.out.println("Game Over! The player has died.");
    }

    private void checkFullMapVisibility() {
        if (player.getCollectedItems() >= ITEM_COUNT && !isFullMapVisible) {
            enableFullMapVisibility();
        }
    }

    private void enableFullMapVisibility() {
        isFullMapVisible = true;

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                isFullMapVisible = false;
            }
        }, FULL_MAP_VISIBILITY_DURATION);
    }

    /**
     * Checks if the full map is currently visible.
     *
     * @return true if the full map is visible, false otherwise
     */
    public boolean isFullMapVisible() {
        return isFullMapVisible;
    }

    /**
     * Retrieves the player's current position.
     *
     * @return a Position object representing the player's current position
     */
    public Position getPlayerPosition() {
        return player.getPosition();
    }

    /**
     * Checks if the game has been won.
     *
     * @return true if the game is won, false otherwise
     */
    public boolean isGameWon() {
        return isGameWon;
    }

    /**
     * Retrieves the player object.
     *
     * @return the Player object representing the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Retrieves the maze object.
     *
     * @return the Maze object representing the game maze
     */
    public Maze getMaze() {
        return maze;
    }

    /**
     * Retrieves the list of characters in the game, including the player and ghosts.
     *
     * @return a List of Character objects
     */
    public List<Character> getCharacters() {
        return characters;
    }
}
