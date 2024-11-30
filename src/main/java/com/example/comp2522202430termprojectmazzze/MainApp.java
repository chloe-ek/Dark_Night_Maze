package com.example.comp2522202430termprojectmazzze;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(final Stage primaryStage) {

        IntroScreen introScreen = new IntroScreen(primaryStage);
        introScreen.showIntro();
    }

    public static void main(final String[] args) {
        launch(args);
    }
}
