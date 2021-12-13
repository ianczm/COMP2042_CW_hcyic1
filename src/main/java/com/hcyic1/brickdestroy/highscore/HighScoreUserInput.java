package com.hcyic1.brickdestroy.highscore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class HighScoreUserInput implements ActionListener {

    private static final int WINDOW_WIDTH = 300;
    private static final int WINDOW_HEIGHT = 150;
    private static final String USER_FIELD_NAME = "Username";
    private static final String USERNAME_CONFIRM_LABEL = "Confirm";

    private final JFrame frame;
    private final JPanel panel;
    private final JLabel usernameFieldName;
    private final JTextField usernameInputField;
    private final JButton submit;

    private final HighScore player;

    public HighScoreUserInput(HighScore player) {

        this.player = player;

        frame = new JFrame();
        frame.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));

        panel = new JPanel();

        usernameFieldName = new JLabel(USER_FIELD_NAME);
        usernameFieldName.setBounds(10, 20, 120, 25);

        usernameInputField = new JTextField();
        usernameInputField.setBounds(100, 20, 165, 25);

        submit = new JButton(USERNAME_CONFIRM_LABEL);
        submit.setBounds(10, 80, 80, 25);
        submit.addActionListener(this);

    }

    public synchronized void showUserPrompt() {
        frame.pack();
        frame.add(panel);

        frame.setLocationRelativeTo(null);

        panel.setLayout(null);
        panel.add(usernameFieldName);
        panel.add(usernameInputField);
        panel.add(submit);

        frame.setVisible(true);

        try {
            wait();
        } catch (InterruptedException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        player.setName(usernameInputField.getText());
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        notifyAll();
    }
}
