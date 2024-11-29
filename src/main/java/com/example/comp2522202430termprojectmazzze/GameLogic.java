package com.example.comp2522202430termprojectmazzze;

import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class GameLogic implements Serializable {
    private static final double GHOST_DETECTION_RADIUS = 3.0;
    private transient Image itemImage = ImageLoader.getInstance().loadImage("/images/item.png");


    private final Maze maze;
    private final Player player;
    private final List<Character> characters;
    private final List<Item> items;
    private boolean isFullMapVisible = false;
    private boolean isGameWon = false;
    private transient SoundManager soundManager;

    private Object readResolve() {
        System.out.println("readResolve called: Reinitializing transient fields.");
        soundManager = new SoundManager();
        itemImage = ImageLoader.getInstance().loadImage("/images/item.png");
        return this;
    }

    public GameLogic(final int width, final int height) {
        maze = new Maze(width, height);
        maze.generateMaze();

        player = new Player(new Position(width / 2, height / 2));
        characters = new ArrayList<>();
        characters.add(player);
        for (int i = 0; i < 5; i++) {
            characters.add(new Ghost(maze.getRandomFreePosition(), 1000));
        }
        items = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            items.add(new Item(maze.getRandomFreePosition()));
        }
        soundManager = new SoundManager();
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }

    public void setSoundManager(final SoundManager soundManager) {
        this.soundManager = soundManager;
    }

    public List<Item> getItems() {
        return items;
    }

    public Image getItemImage() {
        return itemImage;
    }

    public void update() {
        if (!player.isAlive() || isGameWon) {
            return;
        }

        if (player.getPosition().equals(maze.getExitPosition())) {
            isGameWon = true;
            handleGameWin();
            return;
        }

        boolean ghostNearby = false;
        for (Character character : characters) {
            character.update(maze.getStructure());
            if (character instanceof Ghost) {
                double distance = player.getPosition().distanceTo(character.getPosition());
                if (distance <= GHOST_DETECTION_RADIUS) {
                    ghostNearby = true;
                }
            }
        }

        if (soundManager != null) {
            if (ghostNearby) {
                soundManager.playGhostSound();
            } else {
                soundManager.stopGhostSound();
            }
        } else {
            System.err.println("Warning: SoundManager is null in update().");
        }

        items.removeIf(item -> {
            if (player.getPosition().equals(item.getPosition())) {
                player.collectItem();
                checkFullMapVisibility();
                return true;
            }
            return false;
        });

        // Ghost와 충돌 확인
        for (Character character : characters) {
            if (character instanceof Ghost && player.getPosition().equals(character.getPosition())) {
                player.die();
                handlePlayerDeath();
                break;
            }
        }
    }

    public void handleInput(final KeyEvent event) {
        Direction direction = Direction.fromKeyCode(event.getCode());
        if (direction != null) {
            player.move(direction, maze.getStructure());
        }
    }

    private void handleGameWin() {
        System.out.println("You Win! The player has reached the exit.");
    }

    private void handlePlayerDeath() {
        System.out.println("Game Over! The player has died.");
    }



    private void checkFullMapVisibility() {
        if (player.getCollectedItems() >= 5 && !isFullMapVisible) {
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
        }, 2000);
    }

    public boolean isFullMapVisible() {
        return isFullMapVisible;
    }

    public boolean isGameWon() {
        return isGameWon;
    }

    public Player getPlayer() {
        return player;
    }

    public Maze getMaze() {
        return maze;
    }

    public List<Character> getCharacters() {
        return characters;
    }




}
