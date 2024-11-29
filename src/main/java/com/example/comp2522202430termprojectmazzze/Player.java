package com.example.comp2522202430termprojectmazzze;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

import java.io.Serializable;

public class Player implements Character, Updatable, Serializable {
    private static final long serialVersionUID = 1L;

    private static final String IMAGE_PATH_LEFT = "/images/redhoodie_left.png";
    private static final String IMAGE_PATH = "/images/redhoodie.png";
    private static final String DEAD_IMAGE_PATH = "/images/dead.png"; // 죽음 상태 이미지

    private Position position;
    private transient Image playerImage;
    private transient Image deadImage;
    private Direction currentDirection;
    private int collectedItems = 0;
    private boolean isAlive = true;

    private final Flashlight flashlight;


    public Player(final Position startPosition) {
        this.position = startPosition;
        this.currentDirection = Direction.RIGHT;

        this.flashlight = new Flashlight(this, 3, Color.LIGHTYELLOW, 0.5);


        loadImage(IMAGE_PATH);
        loadDeadImage();
    }

    private Object readResolve() {
        loadImage(IMAGE_PATH);
        loadDeadImage();
        return this;
    }

    private void loadImage(final String imagePath) {
        this.playerImage = ImageLoader.getInstance().loadImage(imagePath);
    }

    private void loadDeadImage() {
        this.deadImage = ImageLoader.getInstance().loadImage(DEAD_IMAGE_PATH);
    }
    public boolean isAlive() {
        return isAlive;
    }

    public void die() {
        isAlive = false;
    }

    public Image getCurrentImage() {
        return isAlive ? playerImage : deadImage; // 상태에 따라 이미지 반환
    }


    public void collectItem() {
        collectedItems++;
    }

    public int getCollectedItems() {
        return collectedItems;
    }
    public Flashlight getFlashlight() {
        return flashlight; // Flashlight 접근 메서드
    }

    @Override
    public Position getPosition() {
        return position;
    }

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

    private boolean isValidMove(final int targetX, final int targetY, final boolean[][] maze) {
        return targetX >= 0 && targetX < maze.length
                && targetY >= 0 && targetY < maze[0].length
                && !maze[targetX][targetY];
    }


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

    @Override
    public void update(final boolean[][] maze) {
    }
}
