package com.hcyic1.brickdestroy.leaderboards;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This is a collection of the Score object that has
 * methods to sort, access and save high scores to
 * a persistent file.
 */
public class Leaderboards {

    public static final String FILEPATH = "highscores.txt";
    private static final int COL_NAME = 0;
    private static final int COL_BALLS_USED = 1;
    private static final int COL_BRICKS_DESTROYED = 2;
    private static final int COL_SCORE = 3;
    private static final int NUM_COLS = 4;
    private static final String DELIMITER = ", ";
    private static final int SCORE_NOT_FOUND = -1;

    public static final int DO_NOT_LOAD_FILE = 100;

    private final ArrayList<Score> scores = new ArrayList<>();
    private File file;

    /**
     * Automatically initialises and loads the
     * high score file saved. Will create one
     * if none exists.
     */
    public Leaderboards() {
        initFile();
        loadHighScoresFromFile();
    }

    /**
     * The constructor that is used for
     * testing which does not load or initialise
     * any leaderboards file.
     * @param mode the DO_NOT_LOAD_FILE flag
     */
    public Leaderboards(int mode) {
        if (mode == DO_NOT_LOAD_FILE) {
            System.out.println("Test mode.");
        }
    }

    /**
     * Reads the leaderboard file or creates one
     * if it does not exist.
     */
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

    /**
     * Once the leaderboards file is pointed to, this
     * method will read each score line by line and
     * append it to the list of scores in memory.
     */
    public void loadHighScoresFromFile() {
        try {
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                loadScoreFromString(reader.nextLine());
            }
            reader.close();
            sortByScores();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Converts a single line of scores into a Score object
     * and adds it to the leaderboards.
     * @param scoreString comma-delimited string to be converted into a score.
     */
    public void loadScoreFromString(String scoreString) {
        Score score = new Score();
        String[] scoreStrings = scoreString.split(DELIMITER, NUM_COLS);
        score.setName(scoreStrings[COL_NAME]);
        score.setBallsUsed(Integer.parseInt(scoreStrings[COL_BALLS_USED]));
        score.setBricksDestroyed(Integer.parseInt(scoreStrings[COL_BRICKS_DESTROYED]));
        score.setScore(Float.parseFloat(scoreStrings[COL_SCORE]));
        // might be duplicated
        scores.add(score);
        sortByScores();
    }

    /**
     * Writes the leaderboards list that is in memory
     * into the leaderboards file for persistence.
     */
    public void saveHighScoresToFile() {
        try {
            FileWriter writer = new FileWriter(FILEPATH);
            sortByScores();
            for (Score score: scores) {
                writer.write(score.scoreToString());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Holds the logic behind whether to add a score
     * into the leaderboards or not. Will check existing
     * scores to see if they are lower than the new incoming one
     * for the current user,
     * and sorts from best to worst score.
     * @param score score to be added.
     */
    public void addOrUpdateScore(Score score) {

        if (scoreExists(score)) {
            Score existing = scores.get(getIdxByName(score.getName()));
            if (score.isBetterThan(existing)) {
                scores.remove(existing);
                scores.add(score);
                sortByScores();
                saveHighScoresToFile();
            } else if (existing.equals(score)) {
                // if the current score is the best score
                sortByScores();
                saveHighScoresToFile();
            }
        } else {
            scores.add(score);
            sortByScores();
            saveHighScoresToFile();
        }
    }

    private boolean scoreExists(Score score) {
        return getIdxByName(score.getName()) != SCORE_NOT_FOUND;
    }

    private void sortByScores() {
        int max;
        Score temp;
        for (int i = 0; i < scores.size(); i++) {
            max = i;
            for (int j = i + 1; j < scores.size(); j++) {
                if (scores.get(j).getScore() > scores.get(max).getScore()) {
                    max = j;
                }
            }
            temp = scores.get(i);
            scores.set(i, scores.get(max));
            scores.set(max, temp);
        }
    }

    /**
     * Returns the score in the leaderboards list
     * that matches the input name.
     * @param name name string
     * @return ranking of user, else -1 if not found.
     */
    public Score getHighScoreByName(String name) {
        return scores.get(getIdxByName(name));
    }

    private int getIdxByName(String name) {
        for (int i = 0; i < scores.size(); i++) {
            Score score = scores.get(i);
            if (score.getName().equals(name)) {
                return i;
            }
        }
        return SCORE_NOT_FOUND;
    }

    /**
     * @return the full list of scores as an ArrayList
     */
    public ArrayList<Score> getHighScores() {
        sortByScores();
        return scores;
    }
}
