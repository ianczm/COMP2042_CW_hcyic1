package com.hcyic1.highscore;

import java.lang.Math;

public class HighScore {
    // future: could take brick hardness and time into account
    // scoreMultiplier: score is determined by most recent scoreMultiplier only

    public static final float CLAY_BRICK_REWARD = 1.0F;
    public static final float STEEL_BRICK_REWARD = 1.5F;
    public static final float CEMENT_BRICK_REWARD = 2.0F;

    public static final float BASE_SCORE_MULTIPLIER = 1.0F;
    private float scoreMultiplier;
    private int currentBallBrickCount;

    private String name;
    private int ballsUsed;
    private int bricksDestroyed;
    private float score;

    public HighScore() {
    }

    public HighScore(String name) {
        this.name = name;
        this.ballsUsed = 1;
        this.bricksDestroyed = 0;
        this.currentBallBrickCount = 0;
        resetScore();
        resetScoreMultiplier();
    }

    public HighScore(String name, int ballsUsed, int bricksDestroyed) {
        this.name = name;
        this.ballsUsed = ballsUsed;
        this.bricksDestroyed = bricksDestroyed;
        resetScore();
        resetScoreMultiplier();
    }

    // score calculation

    /**
     * Reward the user with score for destroying bricks
     * and controlling the ball well (not letting it fall)
     */
    public void updateScore() {
        score += CLAY_BRICK_REWARD * scoreMultiplier;
    }

    public void resetScore() {
        score = 0;
    }

    // setters

    public void setScore(float score) {
        this.score = score;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Score can be reset to 1.0 if ball missed.
     */
    public void resetScoreMultiplier() {
        scoreMultiplier = BASE_SCORE_MULTIPLIER;
        currentBallBrickCount = 0;
    }

    /**
     * Follows a logarithmic curve that is a
     * function of bricks hit
     */
    public void updateScoreMultiplier() {
        float calc = (float) currentBallBrickCount / 5;
        scoreMultiplier = (float) Math.log(calc + 1) + 1;
    }

    /**
     * Increments the balls used by 1. Added for easier
     * implementation of real-time high score updates.
     */
    public void incBallsUsed() {
        this.ballsUsed += 1;
    }

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

    public void setBricksDestroyed(int bricksDestroyed) {
        this.bricksDestroyed = bricksDestroyed;
    }

    // getters

    public String getName() {
        return name;
    }

    public int getBallsUsed() {
        return ballsUsed;
    }

    public int getBricksDestroyed() {
        return bricksDestroyed;
    }

    public float getScore() {
        return score;
    }

    public float getScoreMultiplier() {
        return scoreMultiplier;
    }
}
