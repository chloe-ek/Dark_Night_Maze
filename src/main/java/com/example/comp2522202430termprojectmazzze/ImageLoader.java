package com.example.comp2522202430termprojectmazzze;

import javafx.scene.image.Image;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Provides a singleton utility for loading and caching images.
 * This class ensures efficient image management by storing loaded images in memory,
 * preventing redundant loading of the same image.
 *
 * @author Eunji
 * @version 2024
 */
public final class ImageLoader {

    private static ImageLoader instance;
    private final Map<String, Image> imageCache;

    /**
     * Initializes the ImageLoader with an empty image cache.
     * This constructor is private to enforce the singleton pattern.
     */
    private ImageLoader() {
        imageCache = new HashMap<>();
    }

    /**
     * Retrieves the singleton instance of ImageLoader.
     * If no instance exists, creates one.
     *
     * @return the singleton instance of ImageLoader
     */
    public static synchronized ImageLoader getInstance() {
        if (instance == null) {
            instance = new ImageLoader();
        }
        return instance;
    }

    /**
     * Loads an image from the specified path.
     * If the image is already cached, retrieves it from the cache.
     * Otherwise, loads it from the file system, stores it in the cache, and returns it.
     *
     * @param imagePath the path to the image file as a String
     * @return the loaded Image, or null if loading fails
     * @throws IllegalArgumentException if the image resource is not found
     */
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
        } catch (NullPointerException e) {
            System.err.println("Failed to load image: Resource not found at path " + imagePath);
        } catch (IllegalArgumentException e) {
            System.err.println("Failed to load image: Invalid argument " + imagePath);
        }
        return null;
    }
}
