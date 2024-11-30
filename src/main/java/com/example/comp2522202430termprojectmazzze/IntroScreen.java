package com.example.comp2522202430termprojectmazzze;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Represents the introduction screen for the Dark Night Maze game.
 * Displays the game title, a background image, and a start button.
 * On clicking the start button, the main game screen is launched.
 *
 * @author Eunji
 * @version 2024
 */
public class IntroScreen {

    private static final double SCENE_WIDTH = 1000.0;
    private static final double SCENE_HEIGHT = 800.0;
    private static final double TITLE_FONT_SIZE = 30.0;
    private static final double BUTTON_FONT_SIZE = 20.0;
    private static final double LAYOUT_SPACING = 20.0;
    private static final String TITLE_FONT_FAMILY = "Comic Sans MS";
    private static final String BUTTON_FONT_FAMILY = "Arial";
    private static final String BUTTON_STYLE
            = "-fx-background-color: #2E8B57; -fx-text-fill: white;";

    private final Stage stage;

    /**
     * Initializes the IntroScreen with the specified stage.
     *
     * @param stage the primary stage used for displaying the intro screen
     */
    public IntroScreen(final Stage stage) {
        this.stage = stage;
    }

    /**
     * Displays the introduction screen with a title, background image, and a start button.
     */
    public void showIntro() {
        ImageLoader imageLoader = ImageLoader.getInstance();
        var backgroundImage = imageLoader.loadImage("/images/dark.png");

        ImageView imageView = new ImageView(backgroundImage);
        imageView.setFitWidth(SCENE_WIDTH);
        imageView.setFitHeight(SCENE_HEIGHT);
        imageView.setPreserveRatio(false);

        Text title = new Text("Dark Night Maze");
        title.setFont(new Font(TITLE_FONT_FAMILY, TITLE_FONT_SIZE));
        title.setFill(Color.WHITE);

        Button startButton = new Button("START");
        startButton.setFont(new Font(BUTTON_FONT_FAMILY, BUTTON_FONT_SIZE));
        startButton.setStyle(BUTTON_STYLE);
        startButton.setOnAction(_ -> startGame());

        VBox layout = new VBox(LAYOUT_SPACING, title, startButton);
        layout.setStyle("-fx-alignment: center;");

        StackPane root = new StackPane(imageView, layout);
        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Launches the main game screen by initializing the GameController.
     */
    private void startGame() {
        GameController gameController = new GameController();
        gameController.showGameScreen(stage);
    }
}
