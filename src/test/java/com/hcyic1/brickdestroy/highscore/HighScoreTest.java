package com.hcyic1.brickdestroy.highscore;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class HighScoreTest {

    private static HighScore test;

    @BeforeAll
    public static void init() {
        // given
        test = new HighScore("Test", 2, 23, 54.6F);
    }

    @Test
    void shouldIncScore() {
        // when
        test.incScore();
        // then
        Assertions.assertEquals(54.6F, test.getScore());
    }

    @Test
    void shouldResetScore() {
        // when
        test.resetScore();
        // then
        Assertions.assertEquals(0, test.getScore());
    }

    @Test
    void shouldNotBeDefaultScoreMultiplierAfterUpdate() {
        // when
        test.incBricksDestroyed();
        test.updateScoreMultiplier();
        // then
        Assertions.assertNotEquals(1.0F, test.getScoreMultiplier());
    }
}