package com.example.comp2522202430termprojectmazzze;

import javafx.scene.media.AudioClip;
import java.util.Objects;

/**
 * Manages the playback of sound effects in the game, specifically for ghost warnings.
 * Provides methods to play and stop the ghost sound effect, ensuring it does not overlap.
 *
 * @author Eunji
 * @version 2024
 */
public class SoundManager {
    private final AudioClip ghostSound;

    /**
     * Initializes the SoundManager and loads the ghost warning sound effect.
     * The sound file must be located in the specified resource path.
     */
    public SoundManager() {
        ghostSound = new AudioClip(Objects.requireNonNull(getClass().
                getResource("/sounds/warning.mp3")).toExternalForm());
    }

    /**
     * Plays the ghost warning sound if it is not already playing.
     */
    public void playGhostSound() {
        if (!ghostSound.isPlaying()) {
            ghostSound.play();
        }
    }

    /**
     * Stops the ghost warning sound if it is currently playing.
     */
    public void stopGhostSound() {
        if (ghostSound.isPlaying()) {
            ghostSound.stop();
        }
    }
}
