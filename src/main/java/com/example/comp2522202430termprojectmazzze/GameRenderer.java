package com.example.comp2522202430termprojectmazzze;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;


public class GameRenderer {

    public void render(final GraphicsContext gc, final GameLogic gameLogic, final int tileSize, final Flashlight flashlight) {
        renderMaze(gc, gameLogic.getMaze(), tileSize);
        // render the character

        for (Character character : gameLogic.getCharacters()) {
            if (character instanceof Player && !((Player) character).isAlive()) {
                // 플레이어가 죽었으면 게임 오버 메시지 표시
                gc.setFill(Color.RED);
                gc.fillText("Game Over", gc.getCanvas().getWidth() / 2 - 50, gc.getCanvas().getHeight() / 2);
                return; // 렌더링 종료
            }
            character.render(gc, tileSize);
        }
        // render the items
        for (Item item : gameLogic.getItems()) {
            item.render(gc, tileSize);
        }
        // render the flashlight effect
        if (!gameLogic.isFullMapVisible() && flashlight != null) {
            applyFlashlightEffect(gc, flashlight, tileSize);
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
        boolean[][] structure = maze.getStructure();

        for (int x = 0; x < structure.length; x++) {
            for (int y = 0; y < structure[0].length; y++) {
                if (structure[x][y]) {
                    gc.setFill(Color.DARKGREY);
                } else {
                    gc.setFill(javafx.scene.paint.Color.BLACK);
                }
                gc.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
            }
            maze.render(gc, tileSize);


        }
    }

}
