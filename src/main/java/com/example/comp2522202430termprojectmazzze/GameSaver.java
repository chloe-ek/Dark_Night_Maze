package com.example.comp2522202430termprojectmazzze;

import java.io.*;

public class GameSaver {
    public static void saveGame(final GameLogic gameLogic, final String filePath) {
        File file = new File(filePath);
        File parentDir = file.getParentFile();

        // 디렉터리가 없으면 생성
        if (!parentDir.exists()) {
            System.out.println("Creating directories for path: " + parentDir);
            if (!parentDir.mkdirs()) {
                System.out.println("Failed to create directories. Check permissions.");
                return;
            }
        }

        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameLogic);
            System.out.println("Game saved successfully to: " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to save game.");
        }
    }

    public static GameLogic loadGame(final String filePath) {
        File saveFile = new File(filePath);
        if (!saveFile.exists()) {
            System.out.println("Save file does not exist: " + filePath);
            return null;
        }

        try (FileInputStream fis = new FileInputStream(filePath);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (GameLogic) ois.readObject(); // 역직렬화
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to load game.");
            return null;
        }
    }

}

