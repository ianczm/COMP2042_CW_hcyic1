/*
 *  Brick Destroy - A simple Arcade video game
 *   Copyright (C) 2017  Filippo Ranza
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.hcyic1.brickdestroy.view;

import com.hcyic1.brickdestroy.highscore.HighScore;
import com.hcyic1.brickdestroy.highscore.HighScoreUserInput;
import com.hcyic1.brickdestroy.model.game.GameBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

/**
 * The graphical interface of the entire game.
 */
public class GameFrame extends JFrame implements WindowFocusListener {

    private static final String DEF_TITLE = "Brick Destroy";
    private static final int SPLASH_WIDTH = 450;
    private static final int SPLASH_HEIGHT = 300;

    private final GameBoard gameBoard;
    private final SplashFrame splashFrame;

    private boolean windowFocused;

    /**
     * Constructs GameFrame that initialises an instance each of the board
     * and splash screens. Is configured to display splashScreen first.
     */
    public GameFrame() {

        super();

        windowFocused = false;

        this.setLayout(new BorderLayout());

        gameBoard = new GameBoard(this);
        splashFrame = new SplashFrame(this, new Dimension(SPLASH_WIDTH, SPLASH_HEIGHT));

        displaySplashScreen();

    }

    /**
     * Displays window on main display based on pre-set
     * window size, appearance and behavior.
     */
    public void initialize() {
        this.setTitle(DEF_TITLE);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.autoCentreWindow();
        this.setVisible(true);
    }

    /**
     * Display gameBoard. This method assumes nothing
     * else is currently displayed.
     */
    private void displaySplashScreen() {
        this.add(splashFrame, BorderLayout.CENTER);
        this.setUndecorated(true);
    }

    /**
     * Remove splashScreen and display gameBoard.
     * This method assumes splashScreen is already displayed.
     */
    public void displayGameBoard() {

        this.dispose();
        this.remove(splashFrame);
        this.add(gameBoard, BorderLayout.CENTER);
        this.setUndecorated(false);
        initialize();
        /*to avoid problems with graphics, focus controller is added here*/
        this.addWindowFocusListener(this);
    }

    /**
     * Centres the window on the main display.
     */
    private void autoCentreWindow() {
        this.setLocationRelativeTo(null);
    }

    /**
     * Sets gaming state to <code>true</code> when window is active.
     */
    @Override
    public void windowGainedFocus(WindowEvent windowEvent) {
        windowFocused = true;
    }

    /**
     * Switches the game to a lost-focus and stopped state.
     */
    @Override
    public void windowLostFocus(WindowEvent windowEvent) {
        if (windowFocused)
            gameBoard.stopGameOnFocusLoss();
    }
}
