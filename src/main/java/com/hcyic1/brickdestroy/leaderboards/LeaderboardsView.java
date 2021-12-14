package com.hcyic1.brickdestroy.leaderboards;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

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

    public LeaderboardsView(Leaderboards leaderboards) {
        this.leaderboards = leaderboards;
        initHighScoreView();
    }

    public void initHighScoreView() {

        frame = new JFrame();
        frame.setTitle(WINDOW_TITLE);
        frame.setSize(500, 500);

        String[][] highScoreStrings = generateHighScoreStrings();
        table = new JTable(highScoreStrings, HEADERS);

        scrollPane = new JScrollPane(table);
        frame.add(scrollPane);
    }

    public void updateHighScoreView() {
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

    public void showScoreTable() {
        updateHighScoreView();
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}