package com.example.comp2522202430termprojectmazzze;

import javafx.scene.image.Image;

import java.util.Objects;

public class ImageLoader {
    public static Image loadImage(final String imagePath) {
        try {
            return new Image(Objects.requireNonNull(
                    ImageLoader.class.getResourceAsStream(imagePath),
                    "Resource not found: " + imagePath
            ));
        } catch (Exception e) {
            System.out.println("Failed to load image: " + imagePath);
            e.printStackTrace();
            return null;
        }
    }
}
