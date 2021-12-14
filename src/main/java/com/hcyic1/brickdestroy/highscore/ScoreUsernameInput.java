package com.hcyic1.brickdestroy.highscore;

import javax.swing.*;
import java.awt.*;

public class ScoreUsernameInput {

    private static final int WINDOW_WIDTH = 300;
    private static final int WINDOW_HEIGHT = 150;

    private final JFrame frame;

    private final Score player;

    public ScoreUsernameInput(Score player) {

        this.player = player;
        frame = new JFrame();
        frame.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));

    }

    public void updateUsername() {
        player.setName(
            (String) JOptionPane.showInputDialog(
                frame,
                "Input your username:",
                "Username",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                System.getProperty("user.name")
            )
        );
    }
}
