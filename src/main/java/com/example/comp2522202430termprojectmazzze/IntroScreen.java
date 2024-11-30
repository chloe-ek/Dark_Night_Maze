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

public class IntroScreen {
    private final Stage stage;

    public IntroScreen(final Stage stage) {
        this.stage = stage;
    }

    public void showIntro() {

        ImageLoader imageLoader = ImageLoader.getInstance();
        var backgroundImage = imageLoader.loadImage("/images/dark.png");

        ImageView imageView = new ImageView(backgroundImage);

        imageView.setFitWidth(1000);
        imageView.setFitHeight(800);
        imageView.setPreserveRatio(false);

        Text title = new Text("Dark Night Maze");
        title.setFont(new Font("Comic Sans MS", 30));
        title.setFill(Color.WHITE);

        Button startButton = new Button("START");
        startButton.setFont(new Font("Arial", 20));
        startButton.setStyle("-fx-background-color: #2E8B57; -fx-text-fill: white;");
        startButton.setOnAction(event -> startGame());

        VBox layout = new VBox(20, title, startButton);
        layout.setStyle("-fx-alignment: center;");

        StackPane root = new StackPane(imageView, layout);
        Scene scene = new Scene(root, 1000, 800);

        stage.setScene(scene);
        stage.show();
    }

    private void startGame() {
        GameController gameController = new GameController();
        gameController.showGameScreen(stage);
    }

}