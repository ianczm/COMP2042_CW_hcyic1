package com.hcyic1.brickdestroy.highscore;

import java.lang.Math;
import java.util.ArrayList;

/**
 * Represents an individual player's score.
 * @author Ian Chong
 */
public class Score {

    /**
     * Each brick is worth this amount. Might consider setting different
     * score multipliers for different bricks.
     */
    public static final float BASE_SCORE_MULTIPLIER = 1.0F;

    private static final float BASE_BRICK_REWARD = 1.0F;

    private float scoreMultiplier;
    private int currentBallBrickCount;

    private String name;
    private int ballsUsed;
    private int bricksDestroyed;
    private float score;

    /**
     * Empty constructor for score that initialises a zero-score object.
     */
    public Score() {
        this.ballsUsed = 0;
        this.bricksDestroyed = 0;
        this.currentBallBrickCount = 0;
        resetScore();
        resetScoreMultiplier();
    }

    /**
     * Constructor that generates a fully populated score. For use
     * when testing or inputting a new user score.
     * @param name              user's name
     * @param ballsUsed         number of balls used during the game
     * @param bricksDestroyed   number of bricks destroyed during the game
     * @param score             user's score at the end of the game
     */
    public Score(String name, int ballsUsed, int bricksDestroyed, float score) {
        this.name = name;
        this.ballsUsed = ballsUsed;
        this.bricksDestroyed = bricksDestroyed;
        this.score = score;
    }

    /**
     * Reward the user by incrementing score for destroying bricks
     * and keeping the ball bouncing.
     */
    public void incScore() {
        score += BASE_BRICK_REWARD * scoreMultiplier;
    }

    /**
     * Reset the user's score to zero.
     */
    public void resetScore() {
        score = 0;
    }

    /**
     * Set the user's score. Mostly for testing purposes.
     * @param score         new score
     */
    public void setScore(float score) {
        this.score = score;
    }

    /**
     * Sets the name of the user, used when receiving user input
     * to save a new name.
     * @param name          the user's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * To reset the multiplier to 1.0 after a ball
     * has been missed.
     */
    public void resetScoreMultiplier() {
        scoreMultiplier = BASE_SCORE_MULTIPLIER;
        currentBallBrickCount = 0;
    }

    /**
     * Increment the score multiplier following a logarithmic
     * curve that is a function of bricks hit to reward the user for
     * getting the first few combos, but prevent impossibly high scores
     * down the line.
     */
    public void updateScoreMultiplier() {
        float calc = (float) currentBallBrickCount / 5;
        scoreMultiplier = (float) Math.log(calc + 1) + 1;
    }

    /**
     * To record a new ball being used after one has been missed.
     */
    public void incBallsUsed() {
        this.ballsUsed += 1;
    }

    /**
     * Sets the balls used for the user. Mostly for testing purposes.
     * @param ballsUsed number of balls used
     */
    public void setBallsUsed(int ballsUsed) {
        this.ballsUsed = ballsUsed;
    }

    /**
     * Increments the bricks broken used by 1. Added for easier
     * implementation of real-time high score updates.
     */
    public void incBricksDestroyed() {
        this.bricksDestroyed += 1;
        this.currentBallBrickCount += 1;
    }

    /**
     * Sets the number of bricks destroyed by the user. Mostly for testing purposes.
     * @param bricksDestroyed number of bricks destroyed by the user
     */
    public void setBricksDestroyed(int bricksDestroyed) {
        this.bricksDestroyed = bricksDestroyed;
    }

    /**
     * @return string of the username.
     */
    public String getName() {
        return name;
    }

    /**
     * @return int of number of balls used.
     */
    public int getBallsUsed() {
        return ballsUsed;
    }

    /**
     * @return int of number of bricks destroyed,
     */
    public int getBricksDestroyed() {
        return bricksDestroyed;
    }

    /**
     * Mostly used for sorting and displaying to the UI.
     * @return float of the user's score.
     */
    public float getScore() {
        return score;
    }

    /**
     * Mostly used to display to the UI.
     * @return float of the user's current score multiplier.
     */
    public float getScoreMultiplier() {
        return scoreMultiplier;
    }

    /**
     * Converts the properties of Score into a string
     * representation. Mostly for testing, debugging and file output.
     * @return comma-delimited string
     */
    public String scoreToString() {
        return String.format(
                "%s, %d, %d, %.1f\n",
                this.getName(),
                this.getBallsUsed(),
                this.getBricksDestroyed(),
                this.getScore()
        );
    }

    /**
     * Separates the score string by commas into columns.
     * Mostly for generating tables.
     * @return an 2D array of cells.
     */
    public String[] scoreToSeparatedStrings() {
        ArrayList<String> res = new ArrayList<>();
        res.add(this.name);
        res.add(String.format("%d", this.getBallsUsed()));
        res.add(String.format("%d", this.getBricksDestroyed()));
        res.add(String.format("%.1f", this.getScore()));
        return res.toArray(new String[0]);
    }

    /**
     * Compares two Score objects by the score property which is a float.
     * Useful for direct comparison.
     * @param score the Score object to be compared.
     * @return a boolean value.
     */
    public boolean isBetterThan(Score score) {
        int res = Float.compare(this.getScore(), score.getScore());
        return res > 0;
    }
}
