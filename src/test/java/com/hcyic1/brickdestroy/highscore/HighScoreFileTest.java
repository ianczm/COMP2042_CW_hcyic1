package com.hcyic1.brickdestroy.highscore;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class HighScoreFileTest {

    static HighScoreFile testFile;
    static HighScore testScoreNewUser = new HighScore("ayy", 1, 43, 101.4F);
    static HighScore testScoreExistingUser = new HighScore("chloe", 1, 43, 32.6F);


    @BeforeAll
    public static void init() {
        // given
        testFile = new HighScoreFile(HighScoreFile.DO_NOT_LOAD_FILE);
        testFile.loadScoreFromString("hcyic1, 2, 36, 87.4");
        testFile.loadScoreFromString("ianczm, 1, 72, 100.2");
        testFile.loadScoreFromString("chloe, 1, 33, 23.7");
        testFile.loadScoreFromString("ian, 3, 123, 21.1");
    }

    @Test
    void shouldSortAccordingToRankAfterAddition() {
        // when
        testFile.addOrUpdateScore(testScoreNewUser);
        // then
        Assertions.assertEquals(testScoreNewUser.getScore(), testFile.getHighScores().get(0).getScore());
    }

    @Test
    void shouldReplaceExistingUserIfCurrentScoreIsHigher() {
        // when
        testFile.addOrUpdateScore(testScoreExistingUser);
        // then
        Assertions.assertEquals(testScoreExistingUser.getScore(), testFile.getHighScoreByName(testScoreExistingUser.getName()).getScore());
    }


}