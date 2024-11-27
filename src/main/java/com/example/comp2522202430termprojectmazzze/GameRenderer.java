package com.example.comp2522202430termprojectmazzze;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;


public class GameRenderer {

    private long deathAnimationStartTime = 0;

    public void render(final GraphicsContext gc, final GameLogic gameLogic,
                       final int tileSize, final Flashlight flashlight) {
        renderMaze(gc, gameLogic.getMaze(), tileSize);

        if (gameLogic.isGameWon()) {
            renderWinScreen(gc);
            return;
        }


        for (Character character : gameLogic.getCharacters()) {
            if (character instanceof Player player) {
                if (!player.isAlive()) {
                    if (deathAnimationStartTime == 0) {
                        deathAnimationStartTime = System.currentTimeMillis(); // 애니메이션 시작 시간
                    }
                    renderDeathState(gc, player, tileSize);
                    return; // 게임 오버 상태이므로 나머지 렌더링 생략
                }
            }
            character.render(gc, tileSize);
        }

        for (Item item : gameLogic.getItems()) {
            item.render(gc, tileSize);
        }

        if (!gameLogic.isFullMapVisible() && flashlight != null) {
            applyFlashlightEffect(gc, flashlight, tileSize);
        }
    }

    private void renderWinScreen(final GraphicsContext gc) {
        gc.setFill(Color.GREEN);
        gc.fillText("You Win!", gc.getCanvas().getWidth() / 2 - 50, gc.getCanvas().getHeight() / 2);
    }
    private void renderDeathState(final GraphicsContext gc, final Player player, final int tileSize) {
        long elapsedTime = System.currentTimeMillis() - deathAnimationStartTime;

        if (elapsedTime < 2000) { // 2초 동안 죽음 상태 렌더링
            player.render(gc, tileSize); // dead.png 이미지 렌더링
        } else {
            gc.setFill(Color.RED);
            gc.fillText("Game Over", gc.getCanvas().getWidth() / 2 - 50, gc.getCanvas().getHeight() / 2);
        }
    }

    private void applyFlashlightEffect(final GraphicsContext gc,
                                       final Flashlight flashlight, final int tileSize) {
        // 플래시라이트 중심 계산
        Player player = flashlight.getPlayer();
        Position playerPos = player.getPosition();
        double centerX = (playerPos.getCoordinateX() + 1) * tileSize; // 캐릭터 중심
        double centerY = (playerPos.getCoordinateY() + 1) * tileSize; // 캐릭터 중심
        double radius = flashlight.getRadius() * tileSize; // 플래시라이트 반경

        RadialGradient gradient = new RadialGradient(
                0, 0, centerX, centerY, radius, false, CycleMethod.NO_CYCLE,
                new Stop(0.0, Color.LIGHTYELLOW),  // 중심부는 투명
                new Stop(0.4, Color.rgb(0, 0, 0, 0.1)), // 중간은 반투명 검정
                new Stop(1.0, Color.BLACK) // 끝부분은 완전 검정
        );
        gc.setFill(gradient);
        gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    }

    private void renderMaze(final GraphicsContext gc, final Maze maze, final int tileSize) {
        maze.render(gc, tileSize);
    }

}
