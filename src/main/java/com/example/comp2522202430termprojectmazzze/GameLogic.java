package com.example.comp2522202430termprojectmazzze;

import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class GameLogic {
    private final Maze maze;
    private final Player player;
    private final List<Character> characters;
    private final List<Item> items;
    private final SoundManager soundManager = new SoundManager();
    private static final double GHOST_DETECTION_RADIUS = 3.0;
    private boolean isFullMapVisible = false;

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
    }

    public List<Item> getItems() {
        return items;
    }

    public void update() {
        if (!player.isAlive()) {
            return;
        }

        for (Character character : characters) {
                character.update(maze.getStructure());
        }

        items.removeIf(item -> {
            if (player.getPosition().equals(item.getPosition())) {
                player.collectItem();
                checkFullMapVisibility(); // 아이템 수집 후 상태 확인
                return true; // 아이템 제거
            }
            return false;
        });
// 고스트와 충돌 확인
        for (Character character : characters) {
            if (character instanceof Ghost) {
                if (player.getPosition().equals(character.getPosition())) {
                    player.die();
                    handlePlayerDeath();
                    break;
                }
            }
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
        if (ghostNearby) {
            soundManager.playGhostSound();
        } else {
            soundManager.stopGhostSound();
        }
        items.removeIf(item -> player.getPosition().equals(item.getPosition()));

    }

    public void handleInput(final KeyEvent event) {
        Direction direction = Direction.fromKeyCode(event.getCode());
        if (direction != null) {
            player.move(direction, maze.getStructure());
        }
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
