package com.example.comp2522202430termprojectmazzze;


import javafx.scene.media.AudioClip;

public class SoundManager {
    private final AudioClip ghostSound;

    public SoundManager() {
        ghostSound = new AudioClip(getClass().getResource("/sounds/warning.mp3").toExternalForm());
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