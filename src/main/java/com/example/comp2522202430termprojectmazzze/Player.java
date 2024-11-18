package com.example.comp2522202430termprojectmazzze;

public final class Player implements Movement {
    private Position position;


    public Player(final int startX, final int startY) {
        this.position = new Position(startX, startY);
    }
    public Position getPosition() {
        return position;
    }

    private boolean isValidMove(final Position targetPosition, final boolean[][] maze) {
        int x = targetPosition.getX();
        int y = targetPosition.getY();
        return x >= 0 && x < maze.length
                && y >= 0 && y < maze[0].length
                && !maze[x][y];
    }

    @Override
    public void move(final int directionX, final int directionY, final boolean[][] maze) {
        Position nextPosition = new Position(
                position.getX() + directionX,
                position.getY() + directionY
        );

        if (isValidMove(nextPosition, maze)) {
            position = nextPosition;
        }
    }

}
