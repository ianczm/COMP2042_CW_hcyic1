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

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

/**
 * The splash screen of the game that shows game title, version number
 * and start/exit buttons.
 */
public class SplashFrame extends JComponent implements MouseListener, MouseMotionListener {

    private static final String GREETINGS = "Welcome to";
    private static final String GAME_TITLE = "Brick Destroy 2.0";
    private static final String CREDITS = "An adaptation of FillipoRanza's Brick Destroy";
    private static final String CREDITS_2 = "ianczm\\COMP2042_CW_hcyic1";
    private static final String START_TEXT = "Start";
    private static final String INFO_TEXT = "Info";
    private static final String EXIT_TEXT = "Exit";

    private static final String SPLASH_BG_FILEPATH = "src/main/resources/bgImage.jpg";

    private static final Color BG_COLOR = Color.GREEN.darker();
    private static final Color BORDER_COLOR = new Color(200, 8, 21); //Venetian Red
    private static final Color DASH_BORDER_COLOR = new Color(255, 216, 0);//school bus yellow
    private static final Color TEXT_COLOR = new Color(16, 52, 166);//egyptian blue
    private static final Color CLICKED_BUTTON_COLOR = BG_COLOR.brighter();
    private static final Color CLICKED_TEXT = Color.WHITE;
    private static final int BORDER_SIZE = 5;
    private static final float[] DASHES = {12, 6};
    public static final int GREETINGS_FONT_SIZE = 25;
    public static final int TITLE_FONT_SIZE = 40;
    public static final int CREDITS_FONT_SIZE = 10;

    private Rectangle splashScreen;
    private Rectangle startButton;
    private Rectangle exitButton;
    private Rectangle infoButton;

    private final Dimension area;

    private BasicStroke borderStoke;
    private BasicStroke borderStoke_noDashes;

    private Font greetingsFont;
    private Font gameTitleFont;
    private Font creditsFont;
    private Font buttonFont;

    private final GameFrame gameFrame;

    private boolean startClicked;
    private boolean exitClicked;
    private boolean infoClicked;

    private InfoFrame infoFrame = new InfoFrame();

    private final Image splashBg = Toolkit.getDefaultToolkit().getImage(SPLASH_BG_FILEPATH);

    /**
     * Constructor to properly configure the user interface
     * and design elements of the splash screen.
     * @param gameFrame the parent gameFrame that would contain this splash screen.
     * @param area width and height of the parent frame.
     */
    public SplashFrame(GameFrame gameFrame, Dimension area) {

        this.gameFrame = gameFrame;
        this.area = area;

        // might need to remove other area parameters as they are now redundant

        focusWindowAndInput();
        createScreen(area);
        createButtons(area);
        setBorders();
        setFonts();
    }

    /**
     * Makes window focusable and be able
     * to receive input from mouse.
     */
    private void focusWindowAndInput() {
        // window focus
        this.setFocusable(true);
        this.requestFocusInWindow();
        // mouse listener
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    /**
     * Configures the appearance of the splash screen border.
     */
    private void setBorders() {
        borderStoke = new BasicStroke(BORDER_SIZE, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, DASHES, 0);
        borderStoke_noDashes = new BasicStroke(BORDER_SIZE, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    }

    /**
     * Creates the area containing the splash screen.
     * @param area width and height of the parent frame.
     */
    private void createScreen(Dimension area) {
        splashScreen = new Rectangle(new Point(0, 0), area);
        this.setPreferredSize(area);
    }

    /**
     * Generate start and menu buttons for the splash screen.
     * @param area width and height of the parent frame.
     */
    private void createButtons(Dimension area) {
        Dimension btnDim = new Dimension(area.width / 3, area.height / 12);
        startButton = new Rectangle(btnDim);
        exitButton = new Rectangle(btnDim);
        infoButton = new Rectangle(btnDim);
    }

    /**
     * Configure the font face, weight and size of text displayed on the splash screen.
     */
    private void setFonts() {
        greetingsFont = new Font("Noto Mono", Font.PLAIN, GREETINGS_FONT_SIZE);
        gameTitleFont = new Font("Noto Mono", Font.BOLD, TITLE_FONT_SIZE);
        creditsFont = new Font("Monospaced", Font.PLAIN, CREDITS_FONT_SIZE);
        buttonFont = new Font("Monospaced", Font.PLAIN, startButton.height - 2);
    }


    public void paint(Graphics g) {
        drawMenu((Graphics2D) g);
    }


    public void drawMenu(Graphics2D g2d) {

        drawContainer(g2d);

        /*
        all the following method calls need a relative
        painting directly into the HomeMenu rectangle,
        so the translation is made here so the other methods do not do that.
         */
        Color prevColor = g2d.getColor();
        Font prevFont = g2d.getFont();

        double x = splashScreen.getX();
        double y = splashScreen.getY();

        g2d.translate(x, y);

        //methods calls
        drawText(g2d);
        drawButton(g2d);
        //end of methods calls

        g2d.translate(-x, -y);
        g2d.setFont(prevFont);
        g2d.setColor(prevColor);
    }

    private void drawContainer(Graphics2D g2d) {
        Color prev = g2d.getColor();

//        g2d.setColor(BG_COLOR);
//        g2d.fill(splashScreen);

        g2d.drawImage(splashBg, 0, 0, (int) area.getWidth(), (int) area.getHeight(),null);

        Stroke tmp = g2d.getStroke();

        g2d.setStroke(borderStoke_noDashes);
        g2d.setColor(DASH_BORDER_COLOR);
        g2d.draw(splashScreen);

        g2d.setStroke(borderStoke);
        g2d.setColor(BORDER_COLOR);
        g2d.draw(splashScreen);

        g2d.setStroke(tmp);

        g2d.setColor(prev);
    }

    private void drawText(Graphics2D g2d) {

        g2d.setColor(TEXT_COLOR);

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D greetingsRect = greetingsFont.getStringBounds(GREETINGS, frc);
        Rectangle2D gameTitleRect = gameTitleFont.getStringBounds(GAME_TITLE, frc);
        Rectangle2D creditsRect = creditsFont.getStringBounds(CREDITS, frc);
        Rectangle2D credits2Rect = creditsFont.getStringBounds(CREDITS_2, frc);

        int sX, sY;

        sX = (int) (splashScreen.getWidth() - greetingsRect.getWidth()) / 2;
        sY = (int) (splashScreen.getHeight() / 4);

        g2d.setFont(greetingsFont);
        g2d.drawString(GREETINGS, sX, sY);

        sX = (int) (splashScreen.getWidth() - gameTitleRect.getWidth()) / 2;
        sY += (int) gameTitleRect.getHeight() * 0.8;

        g2d.setFont(gameTitleFont);
        g2d.drawString(GAME_TITLE, sX, sY);

        sX = (int) (splashScreen.getWidth() - creditsRect.getWidth()) / 2;
        sY += (int) creditsRect.getHeight() * 1.5;

        g2d.setFont(creditsFont);
        g2d.drawString(CREDITS, sX, sY);

        sX = (int) (splashScreen.getWidth() - credits2Rect.getWidth()) / 2;
        sY += (int) credits2Rect.getHeight() * 1.1;
        g2d.drawString(CREDITS_2, sX, sY);


    }

    private void drawButton(Graphics2D g2d) {

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D startRect = buttonFont.getStringBounds(START_TEXT, frc);
        Rectangle2D exitRect = buttonFont.getStringBounds(EXIT_TEXT, frc);
        Rectangle2D infoRect = buttonFont.getStringBounds(INFO_TEXT, frc);

        g2d.setFont(buttonFont);

        int x = (splashScreen.width - startButton.width) / 2;
        int y = (int) ((splashScreen.height - startButton.height) * 0.6);

        startButton.setLocation(x, y);

        x = (int) (startButton.getWidth() - startRect.getWidth()) / 2;
        y = (int) (startButton.getHeight() - startRect.getHeight()) / 2;

        x += startButton.x;
        y += startButton.y + (startButton.height * 0.9);


        if (startClicked) {
            Color tmp = g2d.getColor();
            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(startButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(START_TEXT, x, y);
            g2d.setColor(tmp);
        } else {
            g2d.draw(startButton);
            g2d.drawString(START_TEXT, x, y);
        }

        x = startButton.x;
        y = startButton.y;

        y *= 1.2;

        infoButton.setLocation(x, y);


        x = (int) (infoButton.getWidth() - infoRect.getWidth()) / 2;
        y = (int) (infoButton.getHeight() - infoRect.getHeight()) / 2;

        x += infoButton.x;
        y += infoButton.y + (startButton.height * 0.9);

        if (infoClicked) {
            Color tmp = g2d.getColor();

            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(infoButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(INFO_TEXT, x, y);
            g2d.setColor(tmp);
        } else {
            g2d.draw(infoButton);
            g2d.drawString(INFO_TEXT, x, y);
        }

        x = infoButton.x;
        y = infoButton.y;

        y *= 1.175;

        exitButton.setLocation(x, y);


        x = (int) (exitButton.getWidth() - exitRect.getWidth()) / 2;
        y = (int) (exitButton.getHeight() - exitRect.getHeight()) / 2;

        x += exitButton.x;
        y += exitButton.y + (infoButton.height * 0.9);

        if (exitClicked) {
            Color tmp = g2d.getColor();

            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(exitButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(EXIT_TEXT, x, y);
            g2d.setColor(tmp);
        } else {
            g2d.draw(exitButton);
            g2d.drawString(EXIT_TEXT, x, y);
        }

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if (startButton.contains(p)) {
            gameFrame.displayGameBoard();
        } else if (exitButton.contains(p)) {
            System.out.println("Goodbye " + System.getProperty("user.name"));
            System.exit(0);
        } else if (infoButton.contains(p)) {
            // show info here.
            infoFrame.showInfoFrame();
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if (startButton.contains(p)) {
            startClicked = true;
            repaint(startButton.x, startButton.y, startButton.width + 1, startButton.height + 1);
        } else if (exitButton.contains(p)) {
            exitClicked = true;
            repaint(exitButton.x, exitButton.y, exitButton.width + 1, exitButton.height + 1);
        } else if (infoButton.contains(p)) {
            infoClicked = true;
            repaint(infoButton.x, infoButton.y, infoButton.width + 1, infoButton.height + 1);
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if (startClicked) {
            startClicked = false;
            repaint(startButton.x, startButton.y, startButton.width + 1, startButton.height + 1);
        } else if (exitClicked) {
            exitClicked = false;
            repaint(exitButton.x, exitButton.y, exitButton.width + 1, exitButton.height + 1);
        } else if (infoClicked) {
            infoClicked = false;
            repaint(infoButton.x, infoButton.y, infoButton.width + 1, infoButton.height + 1);
        }
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
        if (startButton.contains(p) || exitButton.contains(p) || infoButton.contains(p))
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        else
            this.setCursor(Cursor.getDefaultCursor());

    }
}
