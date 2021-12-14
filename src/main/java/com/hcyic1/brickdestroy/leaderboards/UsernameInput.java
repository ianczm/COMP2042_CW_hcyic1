package com.hcyic1.brickdestroy.leaderboards;

import javax.swing.*;
import java.awt.*;

/**
 * The controller for username input that is tied directly
 * to one score/player object.
 */
public class UsernameInput {

    private static final int WINDOW_WIDTH = 300;
    private static final int WINDOW_HEIGHT = 150;

    private final JFrame frame;

    private final Score player;

    /**
     * Attaches this input controller to a score/player object.
     * @param player the score object that requires username input.
     */
    public UsernameInput(Score player) {

        this.player = player;
        frame = new JFrame();
        frame.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));

    }

    /**
     * Pops up a dialog for username entry.
     */
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
