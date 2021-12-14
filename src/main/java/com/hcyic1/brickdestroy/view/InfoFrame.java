package com.hcyic1.brickdestroy.view;

import javax.swing.*;
import java.awt.*;

public class InfoFrame {

    private static final int FRAME_WIDTH = 650;
    private static final int FRAME_HEIGHT = 400;
    public static final String CLOSE_BTN_TEXT = "Close";
    public static final String INFO_HTML_STRING = "<html>" +
            "<h1>Brick Destroy 2.0</h1>" +
            "<p>" +
            "An adaptation of the original Brick Destroy game by FilippoRanza for COMP2042_CW_hcyic1, a software maintenance coursework." +
            "</p>" +
            "<h2>Game Information</h2>" +
            "<p>" +
            "This is a simple arcade video game. The player's goal is to destroy walls with a small ball." +
            "</p>" +
            "<p>Here are key binds to play the game.</p>" +
            "<ul>" +
            "<li><code>SPACE</code> to start or pause the game</li>" +
            "<li><code>A</code> to move left</li>" +
            "<li><code>D</code> to move right</li>" +
            "<li><code>ESC</code> to open the pause menu</li>" +
            "<li><code>ALT</code>+<code>SHIFT</code>+<code>F1</code> to open the console</li>" +
            "<li><code>H</code> to access the high score menu any time</li>" +
            "</ul>" +
            "<p>The game automatically pauses upon focus loss.</p>" +
            "<h2>Scoring System</h2>" +
            "<ul>" +
            "<li>Leaderboards appears after each level, at the end of the game or whenever the you press<code>H</code></li>" +
            "<li>Scores are stored in a permanent <code>highscores.txt</code> file</li>" +
            "<li>Your score increases when bricks are hit</li>" +
            "<li>The more bricks you hit in a row, the higher the score you get per hit</li>" +
            "<li>Your score bonus is reset every time you lose a ball</li>" +
            "</ul>" +
            "</html>";

    private final JFrame infoFrame;

    public InfoFrame() {
        infoFrame = new JFrame();
        infoFrame.setTitle("Brick Destroy Info");
        infoFrame.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        infoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        generateContent();
    }

    public void showInfoFrame() {
        infoFrame.pack();
        infoFrame.setLocationRelativeTo(null);
        infoFrame.setVisible(true);
    }

    private void hideInfoFrame() {
        infoFrame.setVisible(false);
    }

    private void generateContent() {
        JPanel container = new JPanel();
        container.setLayout(new FlowLayout());

        JEditorPane content = new JEditorPane();
        content.setEditable(false);
        content.setContentType("text/html");
        content.setText(INFO_HTML_STRING);

        JScrollPane scroll = new JScrollPane(content);
        scroll.setPreferredSize(new Dimension(FRAME_WIDTH-100, FRAME_HEIGHT-50));

        JButton closeButton = new JButton(CLOSE_BTN_TEXT);
        closeButton.addActionListener(e -> hideInfoFrame());

        container.add(scroll);
        container.add(closeButton);
        infoFrame.getContentPane().add(container, BorderLayout.CENTER);
    }
}
