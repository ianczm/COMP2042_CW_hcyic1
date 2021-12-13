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
package com.hcyic1.brickdestroy.model.game;

import com.hcyic1.brickdestroy.highscore.HighScoreFile;
import com.hcyic1.brickdestroy.highscore.HighScoreUserInput;
import com.hcyic1.brickdestroy.model.ball.Ball;
import com.hcyic1.brickdestroy.model.brick.Brick;
import com.hcyic1.brickdestroy.view.DebugConsole;
import com.hcyic1.brickdestroy.model.platform.Platform;
import com.hcyic1.brickdestroy.highscore.HighScoreView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;

/**
 * The GameBoard contains graphical game-related content
 * to be displayed through the GameFrame.
 */
public class GameBoard extends JComponent implements KeyListener, MouseListener, MouseMotionListener {

    private static final String CONTINUE = "Continue";
    private static final String RESTART = "Restart";
    private static final String EXIT = "Exit";
    private static final String PAUSE = "Pause Menu";
    private static final int TEXT_SIZE = 30;
    private static final Color MENU_COLOR = new Color(0, 255, 0);

    private static final int DEF_WIDTH = 600;
    private static final int DEF_HEIGHT = 450;

    private static final Color BG_COLOR = Color.WHITE;
    public static final int BRICK_COUNT = 30;
    public static final int LINE_COUNT = 3;
    public static final int INITIAL_BALL_POS_X = 300;
    public static final int INITIAL_BALL_POS_Y = 430;
    public static final int BRICK_DIMENSION_RATIO = 6 / 2;

    private Timer gameTimer;

    public Level level;

    private String message;

    private boolean showPauseMenu;

    private Font menuFont;

    private Rectangle continueButtonRect;
    private Rectangle exitButtonRect;
    private Rectangle restartButtonRect;
    private int strLen;

    private DebugConsole debugConsole;

    public HighScoreFile highScoreFile = new HighScoreFile();
    public HighScoreView highScoreView = new HighScoreView(highScoreFile);

    public GameBoard(JFrame owner) {
        super();

        strLen = 0;
        showPauseMenu = false;


        menuFont = new Font("Monospaced", Font.PLAIN, TEXT_SIZE);


        this.initialize();
        message = "";
        level = new Level(new Rectangle(0, 0, DEF_WIDTH, DEF_HEIGHT), BRICK_COUNT, LINE_COUNT, BRICK_DIMENSION_RATIO, new Point(INITIAL_BALL_POS_X, INITIAL_BALL_POS_Y));

        debugConsole = new DebugConsole(owner, level, this);
        //initialize the first level
        level.nextLevel();

        // high score intiialisation
        HighScoreUserInput userInput = new HighScoreUserInput(level.player);
        System.out.println("Generated new userInput object.");
        System.out.println("Updating username.");
        userInput.updateUsername();
        System.out.println(level.player.getName());
        highScoreFile.addOrUpdateScore(level.player);
        System.out.println("Added scores.");

        gameTimer = new Timer(10, e -> {
            level.move();
            level.findImpacts();
            message = String.format(
                    "Bricks: %d Balls: %d Score: %.0f Multiplier: %.2f",
                    level.getBrickCount(),
                    level.getBallCount(),
                    level.player.getScore(),
                    level.player.getScoreMultiplier()
                    );
            if (level.isBallLost()) {
                highScoreFile.addOrUpdateScore(level.player);
                if (level.ballEnd()) {
                    level.wallReset();
                    message = "Game over";
                    highScoreView.showScoreTable();
                }
                level.ballReset();
                gameTimer.stop();
            } else if (level.isDone()) {
                if (level.hasLevel()) {
                    message = "Go to Next Level";
                    // show high score screen
                    highScoreFile.addOrUpdateScore(level.player);
                    highScoreView.showScoreTable();
                    gameTimer.stop();
                    level.ballReset();
                    level.wallReset();
                    level.nextLevel();
                } else {
                    message = "ALL WALLS DESTROYED";
                    highScoreFile.addOrUpdateScore(level.player);
                    highScoreView.showScoreTable();
                    gameTimer.stop();
                }
            }

            repaint();
        });

    }


    private void initialize() {
        this.setPreferredSize(new Dimension(DEF_WIDTH, DEF_HEIGHT));
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }


    public void paint(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        clear(g2d);

        g2d.setColor(Color.BLUE);
        g2d.drawString(message, 175, 225);

        drawBall(level.ball, g2d);

        for (Brick b : level.bricks)
            if (!b.isBroken())
                drawBrick(b, g2d);

        drawPlayer(level.platform, g2d);

        if (showPauseMenu)
            drawMenu(g2d);

        Toolkit.getDefaultToolkit().sync();
    }

    private void clear(Graphics2D g2d) {
        Color tmp = g2d.getColor();
        g2d.setColor(BG_COLOR);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.setColor(tmp);
    }

    private void drawBrick(Brick brick, Graphics2D g2d) {
        Color tmp = g2d.getColor();

        g2d.setColor(brick.getInnerColor());
        g2d.fill(brick.getBrick());

        g2d.setColor(brick.getBorderColor());
        g2d.draw(brick.getBrick());


        g2d.setColor(tmp);
    }

    private void drawBall(Ball ball, Graphics2D g2d) {
        Color tmp = g2d.getColor();

        Shape s = ball.getBallFace();

        g2d.setColor(ball.getInnerColor());
        g2d.fill(s);

        g2d.setColor(ball.getBorderColor());
        g2d.draw(s);

        g2d.setColor(tmp);
    }

    private void drawPlayer(Platform p, Graphics2D g2d) {
        Color tmp = g2d.getColor();

        Shape s = p.getPlatformFace();
        g2d.setColor(Platform.INNER_COLOR);
        g2d.fill(s);

        g2d.setColor(Platform.BORDER_COLOR);
        g2d.draw(s);

        g2d.setColor(tmp);
    }

    private void drawMenu(Graphics2D g2d) {
        obscureGameBoard(g2d);
        drawPauseMenu(g2d);
    }

    private void obscureGameBoard(Graphics2D g2d) {

        Composite tmp = g2d.getComposite();
        Color tmpColor = g2d.getColor();

        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.55f);
        g2d.setComposite(ac);

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, DEF_WIDTH, DEF_HEIGHT);

        g2d.setComposite(tmp);
        g2d.setColor(tmpColor);
    }

    private void drawPauseMenu(Graphics2D g2d) {
        Font tmpFont = g2d.getFont();
        Color tmpColor = g2d.getColor();


        g2d.setFont(menuFont);
        g2d.setColor(MENU_COLOR);

        drawTitleString(g2d, PAUSE);

        int x = this.getWidth() / 8;
        int y = this.getHeight() / 4;


        if (continueButtonRect == null) {
            FontRenderContext frc = g2d.getFontRenderContext();
            continueButtonRect = menuFont.getStringBounds(CONTINUE, frc).getBounds();
            continueButtonRect.setLocation(x, y - continueButtonRect.height);
        }

        g2d.drawString(CONTINUE, x, y);

        y *= 2;

        if (restartButtonRect == null) {
            restartButtonRect = (Rectangle) continueButtonRect.clone();
            restartButtonRect.setLocation(x, y - restartButtonRect.height);
        }

        g2d.drawString(RESTART, x, y);

        y *= 3.0 / 2;

        if (exitButtonRect == null) {
            exitButtonRect = (Rectangle) continueButtonRect.clone();
            exitButtonRect.setLocation(x, y - exitButtonRect.height);
        }

        g2d.drawString(EXIT, x, y);


        g2d.setFont(tmpFont);
        g2d.setColor(tmpColor);
    }

    private void drawTitleString(Graphics2D g2d, String string) {
        if (strLen == 0) {
            FontRenderContext frc = g2d.getFontRenderContext();
            strLen = menuFont.getStringBounds(string, frc).getBounds().width;
        }

        int x = (this.getWidth() - strLen) / 2;
        int y = this.getHeight() / 10;

        g2d.drawString(string, x, y);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_A:
                level.platform.moveLeft();
                break;
            case KeyEvent.VK_D:
                level.platform.movRight();
                break;
            case KeyEvent.VK_ESCAPE:
                showPauseMenu = !showPauseMenu;
                repaint();
                gameTimer.stop();
                break;
            case KeyEvent.VK_SPACE:
                if (!showPauseMenu)
                    if (gameTimer.isRunning())
                        gameTimer.stop();
                    else
                        gameTimer.start();
                break;
            case KeyEvent.VK_F1:
                if (keyEvent.isAltDown() && keyEvent.isShiftDown())
                    debugConsole.setVisible(true);
            case KeyEvent.VK_H:
                // show high score screen
                highScoreFile.addOrUpdateScore(level.player);
                highScoreView.showScoreTable();
            default:
                level.platform.stop();
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        level.platform.stop();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if (!showPauseMenu)
            return;
        if (continueButtonRect.contains(p)) {
            showPauseMenu = false;
            repaint();
        } else if (restartButtonRect.contains(p)) {
            message = "Restarting Game...";
            level.ballReset();
            level.wallReset();
            // need to reset scores
            showPauseMenu = false;
            repaint();
        } else if (exitButtonRect.contains(p)) {
            System.exit(0);
        }

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if (exitButtonRect != null && showPauseMenu) {
            if (exitButtonRect.contains(p) || continueButtonRect.contains(p) || restartButtonRect.contains(p))
                this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            else
                this.setCursor(Cursor.getDefaultCursor());
        } else {
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    public void stopGameOnFocusLoss() {
        gameTimer.stop();
        message = "Focus Lost";
        repaint();
    }

}
