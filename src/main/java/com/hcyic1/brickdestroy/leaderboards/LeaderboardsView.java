package com.hcyic1.brickdestroy.leaderboards;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

/**
 * The object that displays the leaderboards as
 * a table GUI.
 */
public class LeaderboardsView {

    private static final String WINDOW_TITLE = "Brick Destroy High Scores";

    private static final String[] HEADERS = {
        "Player Name",
        "Balls Used",
        "Bricks Destroyed",
        "Score"
    };

    JFrame frame;
    JTable table;
    JScrollPane scrollPane;

    Leaderboards leaderboards;

    /**
     * Constructor that shows a table based on the
     * leaderboard object received.
     * @param leaderboards leaderboards to be displayed.
     */
    public LeaderboardsView(Leaderboards leaderboards) {
        this.leaderboards = leaderboards;
        initHighScoreView();
    }

    private void initHighScoreView() {

        frame = new JFrame();
        frame.setTitle(WINDOW_TITLE);
        frame.setSize(500, 500);

        String[][] highScoreStrings = generateHighScoreStrings();
        table = new JTable(highScoreStrings, HEADERS);

        scrollPane = new JScrollPane(table);
        frame.add(scrollPane);
    }

    private void updateHighScoreView() {
        String[][] highScoreStrings = generateHighScoreStrings();
        DefaultTableModel tableData = new DefaultTableModel(highScoreStrings, HEADERS);
        table.setModel(tableData);
    }

    private String[][] generateHighScoreStrings() {

        ArrayList<String[]> highScoreStrings = new ArrayList<>();

        for (Score score: leaderboards.getHighScores()) {
            highScoreStrings.add(score.scoreToSeparatedStrings());
        }

        return highScoreStrings.toArray(new String[0][]);
    }

    /**
     * Displays the leaderboards as a table.
     */
    public void showScoreTable() {
        updateHighScoreView();
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
