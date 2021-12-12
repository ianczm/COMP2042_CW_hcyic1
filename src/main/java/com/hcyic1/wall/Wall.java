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
package com.hcyic1.wall;

import com.hcyic1.ball.Ball;
import com.hcyic1.ball.RubberBall;
import com.hcyic1.brick.Brick;
import com.hcyic1.brick.CementBrick;
import com.hcyic1.brick.ClayBrick;
import com.hcyic1.brick.SteelBrick;
import com.hcyic1.player.Player;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.concurrent.ThreadLocalRandom;


public class Wall {

    private static final int LEVELS_COUNT = 4;

    private static final int CLAY = 1;
    private static final int STEEL = 2;
    private static final int CEMENT = 3;
    private static final int BALL_COUNT = 3;
    private static final boolean FORCE_NEGATIVE = true;

    public static final int PLAYER_WIDTH = 150;
    public static final int PLAYER_HEIGHT = 10;

    // Ball speeds
    public static final int MAX_SPEED_X = 3;
    public static final int MAX_SPEED_Y = 3;
    public static final int MIN_SPEED_X = -3;
    public static final int MIN_SPEED_Y = 1;

    private Rectangle area;

    // to be shared publicly
    public Brick[] bricks;
    public Ball ball;
    public Player player;

    private Brick[][] levels;
    private int level;

    private Point startPoint;
    private int brickCount;
    private int ballCount;
    private boolean ballLost;

    public Wall(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio, Point ballPos) {

        this.startPoint = new Point(ballPos);

        levels = makeLevels(drawArea, brickCount, lineCount, brickDimensionRatio);
        level = 0;

        ballCount = BALL_COUNT;
        ballLost = false;

        makeBall(ballPos);

        resetBallSpeed();

        player = new Player((Point) ballPos.clone(), PLAYER_WIDTH, PLAYER_HEIGHT, drawArea);

        area = drawArea;


    }

    private void resetBallSpeed() {
        int speedX = initSpeed(MIN_SPEED_X, MAX_SPEED_X, !FORCE_NEGATIVE);
        int speedY = initSpeed(MIN_SPEED_Y, MAX_SPEED_Y, FORCE_NEGATIVE);

        ball.setSpeed(speedX, speedY);
    }

    private int initSpeed(int min, int max, boolean FORCE_NEGATIVE) {
        int speed;

        do {
            speed = randInt(min, max);
        } while (speed == 0);

        if (FORCE_NEGATIVE) {
            speed *= -1;
        }

        return speed;
    }

    private int randInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    private Brick[] makeSingleTypeLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int type) {
        /*
          if brickCount is not divisible by line count,brickCount is adjusted to the biggest
          multiple of lineCount smaller then brickCount
         */
        brickCnt -= brickCnt % lineCnt;

        int brickOnLine = brickCnt / lineCnt;

        double brickLen = drawArea.getWidth() / brickOnLine;
        double brickHgt = brickLen / brickSizeRatio;

        brickCnt += lineCnt / 2;

        Brick[] tmp = new Brick[brickCnt];

        Dimension brickSize = new Dimension((int) brickLen, (int) brickHgt);
        Point p = new Point();

        int i;
        for (i = 0; i < tmp.length; i++) {
            int line = i / brickOnLine;
            if (line == lineCnt)
                break;
            double x = (i % brickOnLine) * brickLen;
            x = (line % 2 == 0) ? x : (x - (brickLen / 2));
            double y = (line) * brickHgt;
            p.setLocation(x, y);
            tmp[i] = makeBrick(p, brickSize, type);
        }

        for (double y = brickHgt; i < tmp.length; i++, y += 2 * brickHgt) {
            double x = (brickOnLine * brickLen) - (brickLen / 2);
            p.setLocation(x, y);
            tmp[i] = new ClayBrick(p, brickSize);
        }
        return tmp;

    }

    private Brick[] makeChessboardLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int typeA, int typeB) {
        /*
          if brickCount is not divisible by line count,brickCount is adjusted to the biggest
          multiple of lineCount smaller then brickCount
         */
        brickCnt -= brickCnt % lineCnt;

        int brickOnLine = brickCnt / lineCnt;

        int centerLeft = brickOnLine / 2 - 1;
        int centerRight = brickOnLine / 2 + 1;

        double brickLen = drawArea.getWidth() / brickOnLine;
        double brickHgt = brickLen / brickSizeRatio;

        brickCnt += lineCnt / 2;

        Brick[] tmp = new Brick[brickCnt];

        Dimension brickSize = new Dimension((int) brickLen, (int) brickHgt);
        Point p = new Point();

        int i;
        for (i = 0; i < tmp.length; i++) {
            int line = i / brickOnLine;
            if (line == lineCnt)
                break;
            int posX = i % brickOnLine;
            double x = posX * brickLen;
            x = (line % 2 == 0) ? x : (x - (brickLen / 2));
            double y = (line) * brickHgt;
            p.setLocation(x, y);

            boolean b = ((line % 2 == 0 && i % 2 == 0) || (line % 2 != 0 && posX > centerLeft && posX <= centerRight));
            tmp[i] = b ? makeBrick(p, brickSize, typeA) : makeBrick(p, brickSize, typeB);
        }

        for (double y = brickHgt; i < tmp.length; i++, y += 2 * brickHgt) {
            double x = (brickOnLine * brickLen) - (brickLen / 2);
            p.setLocation(x, y);
            tmp[i] = makeBrick(p, brickSize, typeA);
        }
        return tmp;
    }

    private void makeBall(Point2D ballPos) {
        ball = new RubberBall(ballPos);
    }

    private Brick[][] makeLevels(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio) {
        Brick[][] tmp = new Brick[LEVELS_COUNT][];
        tmp[0] = makeSingleTypeLevel(drawArea, brickCount, lineCount, brickDimensionRatio, CLAY);
        tmp[1] = makeChessboardLevel(drawArea, brickCount, lineCount, brickDimensionRatio, CLAY, CEMENT);
        tmp[2] = makeChessboardLevel(drawArea, brickCount, lineCount, brickDimensionRatio, CLAY, STEEL);
        tmp[3] = makeChessboardLevel(drawArea, brickCount, lineCount, brickDimensionRatio, STEEL, CEMENT);
        return tmp;
    }

    public void move() {
        player.move();
        ball.move();
    }

    public void findImpacts() {
        if (player.impact(ball)) {
            ball.reverseY();
        } else if (impactWall()) {
            /*for efficiency reverse is done into method impactWall
             * because for every brick, program checks for horizontal and vertical impacts
             */
            brickCount--;
        } else if (hasWallImpact()) {
            ball.reverseX();
        } else if (ball.getPosCenter().getY() < area.getY()) {
            ball.reverseY();
        } else if (ball.getPosCenter().getY() > area.getY() + area.getHeight()) {
            ballCount--;
            ballLost = true;
        }
    }

    private boolean impactWall() {
        for (Brick b : bricks) {
            switch (b.findImpact(ball)) {
                //Vertical Impact
                case Brick.UP_IMPACT -> {
                    ball.reverseY();
                    return b.setImpact(ball.getPosDown(), Brick.Crack.UP);
                }
                case Brick.DOWN_IMPACT -> {
                    ball.reverseY();
                    return b.setImpact(ball.getPosUp(), Brick.Crack.DOWN);
                }

                //Horizontal Impact
                case Brick.LEFT_IMPACT -> {
                    ball.reverseX();
                    return b.setImpact(ball.getPosRight(), Brick.Crack.RIGHT);
                }
                case Brick.RIGHT_IMPACT -> {
                    ball.reverseX();
                    return b.setImpact(ball.getPosLeft(), Brick.Crack.LEFT);
                }
            }
        }
        return false;
    }

    private boolean hasWallImpact() {
        Point2D p = ball.getPosCenter();
        return (impactLeftWall(p) || impactRightWall(p));
    }

    private boolean impactRightWall(Point2D p) {
        return p.getX() > (area.getX() + area.getWidth());
    }

    private boolean impactLeftWall(Point2D p) {
        return p.getX() < area.getX();
    }

    public int getBrickCount() {
        return brickCount;
    }

    public int getBallCount() {
        return ballCount;
    }

    public boolean isBallLost() {
        return ballLost;
    }

    public void ballReset() {
        player.moveTo(startPoint);
        ball.moveTo(startPoint);
        resetBallSpeed();
        ballLost = false;
    }

    public void wallReset() {
        for (Brick b : bricks)
            b.repair();
        brickCount = bricks.length;
        ballCount = BALL_COUNT;
    }

    public boolean ballEnd() {
        return ballCount == 0;
    }

    public boolean isDone() {
        return brickCount == 0;
    }

    public void nextLevel() {
        bricks = levels[level++];
        this.brickCount = bricks.length;
    }

    public boolean hasLevel() {
        return level < levels.length;
    }

    public void setBallXSpeed(int s) {
        ball.setXSpeed(s);
    }

    public void setBallYSpeed(int s) {
        ball.setYSpeed(s);
    }

    public void resetBallCount() {
        ballCount = BALL_COUNT;
    }

    private Brick makeBrick(Point point, Dimension size, int type) {
        return switch (type) {
            case CLAY -> new ClayBrick(point, size);
            case STEEL -> new SteelBrick(point, size);
            case CEMENT -> new CementBrick(point, size);
            default -> throw new IllegalArgumentException(String.format("Unknown Type:%d\n", type));
        };
    }

}
