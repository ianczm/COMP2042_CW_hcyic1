package com.hcyic1.brickdestroy.model.ball;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;

/**
 * Created by filippo on 04/09/16.
 */
abstract public class Ball {

    // increase speed upon impact to make it more fun to play
    public static final float SPEED_Y_LIMIT = 7F;
    public static final float SPEED_X_LIMIT = 5F;
    public static final float SPEED_MULT = 1.05F;

    private Shape ballFace;

    private final Point2D center;
    private Point2D up;
    private Point2D left;
    private Point2D right;
    private Point2D down;

    private final Color border;
    private final Color inner;

    private float speedX;
    private float speedY;

    public Ball(Point2D center, int radiusA, int radiusB, Color inner, Color border) {

        this.center = center;

        initLocation();
        setPoints(radiusA, radiusB);

        ballFace = makeBall(center, radiusA, radiusB);

        this.border = border;
        this.inner = inner;

        setSpeed(0, 0);
    }

    private void initLocation() {
        up = new Point2D.Double();
        down = new Point2D.Double();
        left = new Point2D.Double();
        right = new Point2D.Double();
    }

    protected abstract Shape makeBall(Point2D center, int radiusA, int radiusB);

    public void move() {
        RectangularShape tmp = (RectangularShape) ballFace;
        center.setLocation((center.getX() + speedX), (center.getY() + speedY));
        double w = tmp.getWidth();
        double h = tmp.getHeight();

        tmp.setFrame((center.getX() - (w / 2)), (center.getY() - (h / 2)), w, h);
        setPoints(w, h);


        ballFace = tmp;
    }

    public void setSpeed(int x, int y) {
        speedX = x;
        speedY = y;
    }

    public void incSpeed() {

        if (speedX >= SPEED_X_LIMIT || speedY >= SPEED_Y_LIMIT) {
            speedX = SPEED_X_LIMIT;
            speedY = SPEED_Y_LIMIT;
        } else {
            speedX *= SPEED_MULT;
            speedY *= SPEED_MULT;
        }
    }

    public void setXSpeed(int s) {
        speedX = s;
    }

    public void setYSpeed(int s) {
        speedY = s;
    }

    public void reverseX() {
        speedX *= -1;
    }

    public void reverseY() {
        speedY *= -1;
    }

    public Color getBorderColor() {
        return border;
    }

    public Color getInnerColor() {
        return inner;
    }

    public Point2D getPosCenter() {
        return center;
    }

    public Point2D getPosUp() {
        return up;
    }

    public Point2D getPosDown() {
        return down;
    }

    public Point2D getPosLeft() {
        return left;
    }

    public Point2D getPosRight() {
        return right;
    }

    public Shape getBallFace() {
        return ballFace;
    }

    public void moveTo(Point p) {
        center.setLocation(p);

        RectangularShape tmp = (RectangularShape) ballFace;
        double w = tmp.getWidth();
        double h = tmp.getHeight();

        tmp.setFrame((center.getX() - (w / 2)), (center.getY() - (h / 2)), w, h);
        ballFace = tmp;
    }

    private void setPoints(double width, double height) {
        up.setLocation(center.getX(), center.getY() - (height / 2));
        down.setLocation(center.getX(), center.getY() + (height / 2));

        left.setLocation(center.getX() - (width / 2), center.getY());
        right.setLocation(center.getX() + (width / 2), center.getY());
    }

    public float getSpeedX() {
        return speedX;
    }

    public float getSpeedY() {
        return speedY;
    }


}
