package com.example.comp2522202430termprojectmazzze;


import javafx.scene.media.AudioClip;

import java.util.Objects;

public class SoundManager {
    private final AudioClip ghostSound;

    public SoundManager() {
        ghostSound = new AudioClip(Objects.requireNonNull(getClass().
                getResource("/sounds/warning.mp3")).toExternalForm());
    }

    public void playGhostSound() {
        if (!ghostSound.isPlaying()) {
            ghostSound.play();
        }
    }

    public void stopGhostSound() {
        if (ghostSound.isPlaying()) {
            ghostSound.stop();
        }
    }
}
