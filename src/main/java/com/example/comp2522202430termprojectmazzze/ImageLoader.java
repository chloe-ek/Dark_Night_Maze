package com.example.comp2522202430termprojectmazzze;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ImageLoader {

    //singleton instance
    private static ImageLoader instance;

    //image caching
    private final Map<String, Image> imageCache;

    // private constructor
    private ImageLoader() {
        imageCache = new HashMap<>();
    }

    public static synchronized ImageLoader getInstance() {
        if (instance == null) {
            instance = new ImageLoader();
        }
        return instance;
    }

    public Image loadImage(final String imagePath) {

        if (imageCache.containsKey(imagePath)) {
            return imageCache.get(imagePath);
        }

        try {
            Image image = new Image(Objects.requireNonNull(
                    getClass().getResourceAsStream(imagePath),
                    "Resource not found: " + imagePath
            ));
            imageCache.put(imagePath, image);
            return image;

        } catch (Exception e) {
            System.out.println("Failed to load image: " + imagePath);
            e.printStackTrace();
            return null;
        }
    }
}
