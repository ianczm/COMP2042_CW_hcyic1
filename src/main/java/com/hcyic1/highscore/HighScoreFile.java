package com.hcyic1.highscore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class HighScoreFile {

    public static final String FILEPATH = "highscores.txt";
    private static final int COL_NAME = 0;
    private static final int COL_BALLS_USED = 1;
    private static final int COL_BRICKS_DESTROYED = 2;
    private static final int COL_SCORE = 3;
    private static final int NUM_COLS = 4;
    private static final String DELIMITER = ", ";
    private static final int SCORE_NOT_FOUND = -1;

    private final ArrayList<HighScore> highScores = new ArrayList<>();
    private File file;

    public HighScoreFile() {
        initFile();
        loadHighScoresFromFile();
    }

    public void initFile() {
        try {
            file = new File(FILEPATH);
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getPath());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void loadHighScoresFromFile() {
        try {
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                loadScoreFromString(reader.nextLine());
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void loadScoreFromString(String scoreString) {
        HighScore score = new HighScore();
        String[] scoreStrings = scoreString.split(DELIMITER, NUM_COLS);
        score.setName(scoreStrings[COL_NAME]);
        score.setBallsUsed(Integer.parseInt(scoreStrings[COL_BALLS_USED]));
        score.setBricksDestroyed(Integer.parseInt(scoreStrings[COL_BRICKS_DESTROYED]));
        score.setScore(Float.parseFloat(scoreStrings[COL_SCORE]));
        // might be duplicated
        highScores.add(score);
        sortByScores();
    }

    public void saveHighScoresToFile() {
        try {
            FileWriter writer = new FileWriter(FILEPATH);
            for (HighScore score: highScores) {
                writer.write(scoreToString(score));
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private String scoreToString(HighScore score) {
        return String.format(
                "%s, %d, %d, %.1f",
                score.getName(),
                score.getBallsUsed(),
                score.getBricksDestroyed(),
                score.getScore()
        );
    }

    public void addOrUpdateScore(HighScore score) {
        if (getIdxByName(score.getName()) != SCORE_NOT_FOUND) {
            removeExistingScore(score.getName());
        }
        // might be duplicated
        highScores.add(score);
        sortByScores();
    }

    public void removeExistingScore(String name) {
        // no error handling yet
        highScores.remove(getIdxByName(name));
    }

    private void sortByScores() {
        int min;
        HighScore temp;
        for (int i = 0; i < highScores.size(); i++) {
            min = i;
            for (int j = i + 1; j < highScores.size(); j++) {
                if (highScores.get(j).getScore() < highScores.get(min).getScore()) {
                    min = j;
                }
            }
            temp = highScores.get(i);
            highScores.set(i, highScores.get(min));
            highScores.set(min, temp);
        }
    }

    private int getIdxByName(String name) {
        for (int i = 0; i < highScores.size(); i++) {
            HighScore score = highScores.get(i);
            if (score.getName().equals(name)) {
                return i;
            }
        }
        return SCORE_NOT_FOUND;
    }
}
