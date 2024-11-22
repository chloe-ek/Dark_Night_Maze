package com.example.comp2522202430termprojectmazzze;

import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;

public class GameLogic {
    private final Maze maze;
    private final Player player;
    private final List<Character> characters;
    private final List<Item> items;

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
        for (Character character : characters) {
            character.update(maze.getStructure());
        }
        items.removeIf(item -> player.getPosition().equals(item.getPosition()));

    }

    public void handleInput(final KeyEvent event) {
        Direction direction = Direction.fromKeyCode(event.getCode());
        if (direction != null) {
            player.move(direction, maze.getStructure());
        }
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
