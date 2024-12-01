package com.example.comp2522202430termprojectmazzze;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;

/**
 * Handles the rendering of the game elements including the maze, characters, items,
 * HUD, and visual effects like the flashlight.
 *
 * @author Eunji
 * @version 2024
 */
public class GameRenderer {
    private static final double HUD_START_X = 10.0;
    private static final double HUD_START_Y = 10.0;
    private static final double HUD_ICON_SIZE = 40.0;
    private static final int TEXT_SIZE = 20;
    private static final double HUD_TEXT_OFFSET_X = 10.0;
    private static final double HUD_TEXT_OFFSET_Y = 5.0;
    private static final double TEXT_OFFSET = 50.0;
    private static final int DEATH_ANIMATION_DURATION = 2000;
    private static final double FLASHLIGHT_GRADIENT_STOP_1 = 0.1;
    private static final double FLASHLIGHT_GRADIENT_STOP_2 = 0.4;
    private long deathAnimationStartTime = 0;

    /**
     * Renders the heads-up display (HUD) showing player statistics like collected items.
     *
     * @param gc the GraphicsContext used to render onto the canvas
     * @param player the player whose statistics are displayed in the HUD
     * @param itemImage the image used to represent collected items in the HUD
     */
    public void renderHUD(final GraphicsContext gc, final Player player, final Image itemImage) {
        double hudStartX = HUD_START_X;
        double hudStartY = HUD_START_Y;
        double hudIconSize = HUD_ICON_SIZE;

        gc.drawImage(itemImage, hudStartX, hudStartY, hudIconSize, hudIconSize);

        int collectedItems = player.getCollectedItems();
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Comic Sans MS", TEXT_SIZE));
        gc.fillText("x" + collectedItems, hudStartX + hudIconSize + HUD_TEXT_OFFSET_X,
                hudStartY + hudIconSize - HUD_TEXT_OFFSET_Y);
    }

    /**
     * Renders the current state of the game, including the maze, characters, items, and HUD.
     *
     * @param gc the GraphicsContext used to render onto the canvas
     * @param gameLogic the current game logic containing the state to render
     * @param tileSize the size of each tile in pixels
     */
    public void render(final GraphicsContext gc, final GameLogic gameLogic,
                       final int tileSize) {
        renderMaze(gc, gameLogic.getMaze(), tileSize);

        if (gameLogic.isGameWon()) {
            renderWinScreen(gc);
            return;
        }

        for (Character character : gameLogic.getCharacters()) {
            if (character instanceof Player player) {
                if (player.isAlive()) {
                    if (deathAnimationStartTime == 0) {
                        deathAnimationStartTime = System.currentTimeMillis();
                    }
                    renderDeathState(gc, player, tileSize);
                    return;
                }
            }
            character.render(gc, tileSize);
        }

        for (Item item : gameLogic.getItems()) {
            item.render(gc, tileSize);
        }

        if (!gameLogic.isFullMapVisible()) {
            Player player = gameLogic.getPlayer();
            Flashlight flashlight = player.getFlashlight();
            if (flashlight != null) {
                applyFlashlightEffect(gc, flashlight, tileSize);
            }
        }
        renderHUD(gc, gameLogic.getPlayer(), gameLogic.getItemImage());
    }

    /**
     * Renders the game win screen.
     *
     * @param gc the GraphicsContext used to render onto the canvas
     */
    private void renderWinScreen(final GraphicsContext gc) {
        gc.setFill(Color.GREEN);
        gc.fillText("Maze Master!",
                gc.getCanvas().getWidth() / 2 - TEXT_OFFSET, gc.getCanvas().getHeight() / 2);
    }

    /**
     * Renders the game over state with a death animation and message.
     *
     * @param gc the GraphicsContext used to render onto the canvas
     * @param player the player whose death state is rendered
     * @param tileSize the size of each tile in pixels
     */
    private void renderDeathState(final GraphicsContext gc, final Player player,
                                  final int tileSize) {
        long elapsedTime = System.currentTimeMillis() - deathAnimationStartTime;

        if (elapsedTime < DEATH_ANIMATION_DURATION) {
            player.render(gc, tileSize);
        } else {
            gc.setFill(Color.RED);
            gc.fillText("Game Over", gc.getCanvas().getWidth()
                    / 2 - TEXT_OFFSET, gc.getCanvas().getHeight() / 2);
        }
    }

    /**
     * Applies the flashlight effect around the player, darkening areas outside its radius.
     *
     * @param gc the GraphicsContext used to render onto the canvas
     * @param flashlight the Flashlight object providing the radius, color, and opacity
     * @param tileSize the size of each tile in pixels
     */
    private void applyFlashlightEffect(final GraphicsContext gc,
                                       final Flashlight flashlight, final int tileSize) {
        Player player = flashlight.getPlayer();
        Position playerPos = player.getPosition();
        double centerX = (playerPos.getCoordinateX() + 1) * tileSize;
        double centerY = (playerPos.getCoordinateY() + 1) * tileSize;
        double radius = flashlight.getRadius() * tileSize;

        RadialGradient gradient = new RadialGradient(
                0, 0, centerX, centerY, radius, false, CycleMethod.NO_CYCLE,
                new Stop(0.0, flashlight.getColor()),
                new Stop(FLASHLIGHT_GRADIENT_STOP_1, Color.LIGHTYELLOW),
                new Stop(FLASHLIGHT_GRADIENT_STOP_2, Color.rgb(0, 0, 0,
                        flashlight.getOpacity() * FLASHLIGHT_GRADIENT_STOP_1)),
                new Stop(1.0, Color.BLACK)
        );
        gc.setFill(gradient);
        gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    }

    /**
     * Renders the maze structure on the canvas.
     *
     * @param gc the GraphicsContext used to render onto the canvas
     * @param maze the Maze object to be rendered
     * @param tileSize the size of each tile in pixels
     */
    private void renderMaze(final GraphicsContext gc, final Maze maze, final int tileSize) {
        maze.render(gc, tileSize);
    }
}
