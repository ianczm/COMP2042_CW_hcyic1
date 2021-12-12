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
package com.hcyic1.debug;

import com.hcyic1.wall.Wall;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;


public class DebugPanel extends JPanel {

    private static final Color DEF_BKG = Color.WHITE;
    public static final int GRID_ROWS = 2;
    public static final int GRID_COLS = 2;

    private JSlider ballXSpeed;
    private JSlider ballYSpeed;

    public DebugPanel(Wall wall) {

        initialize();
        addDebugButtons(wall);
        addDebugSliders(wall);

    }

    private void initialize() {
        this.setBackground(DEF_BKG);
        this.setLayout(new GridLayout(GRID_ROWS, GRID_COLS));
    }

    private JButton makeButton(String title, ActionListener e) {
        JButton out = new JButton(title);
        out.addActionListener(e);
        return out;
    }

    private void addDebugButtons(Wall wall) {
        JButton skipLevel = makeButton("Skip Level", e -> wall.nextLevel());
        this.add(skipLevel);

        JButton resetBalls = makeButton("Reset Balls", e -> wall.resetBallCount());
        this.add(resetBalls);
    }

    private JSlider makeSlider(int min, int max, ChangeListener e) {
        JSlider out = new JSlider(min, max);
        out.setMajorTickSpacing(1);
        out.setSnapToTicks(true);
        out.setPaintTicks(true);
        out.addChangeListener(e);
        return out;
    }

    private void addDebugSliders(Wall wall) {
        ballXSpeed = makeSlider(Wall.MIN_SPEED_X, Wall.MAX_SPEED_X, e -> wall.setBallXSpeed(ballXSpeed.getValue()));
        this.add(ballXSpeed);

        ballYSpeed = makeSlider(Wall.MIN_SPEED_Y, Wall.MAX_SPEED_Y, e -> wall.setBallYSpeed(ballYSpeed.getValue()));
        this.add(ballYSpeed);
    }

    public void setSpeeds(int x, int y) {
        ballXSpeed.setValue(x);
        ballYSpeed.setValue(y);
    }

}
