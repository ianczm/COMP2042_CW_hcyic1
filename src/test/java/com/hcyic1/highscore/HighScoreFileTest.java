package com.hcyic1.highscore;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HighScoreFileTest {

    @Test
    void addOrUpdateScore() {

        // given
        HighScoreFile testFile = new HighScoreFile();

        HighScore testScore = new HighScore(
                "hcyic1",
                2,
                800,
                25252
        );

        // when
        testFile.addOrUpdateScore(testScore);

        // then
        assertEquals(testFile.getHighScores().get(0).getScore(), 25252);
    }
}