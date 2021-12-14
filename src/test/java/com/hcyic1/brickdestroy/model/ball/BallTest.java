package com.hcyic1.brickdestroy.model.ball;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.*;

class BallTest {

    private static final Point2D CENTER = new Point(25, 25);
    static RubberBall ballTest;

    @BeforeAll
    public static void init() {
        ballTest = new RubberBall(CENTER);
    }

    @Test
    void shouldSetXSpeed() {
        ballTest.setXSpeed(12);
        assertEquals(12, ballTest.getSpeedX());
    }

    @Test
    void shouldSetYSpeed() {
        ballTest.setYSpeed(12);
        assertEquals(12, ballTest.getSpeedY());
    }

    @Test
    void shouldSetSpeed() {
        ballTest.setSpeed(4, 5);
        assertEquals(4, ballTest.getSpeedX());
        assertEquals(5, ballTest.getSpeedY());
    }

    @Test
    void shouldReverseSpeedX() {
        ballTest.setSpeed(2, -3);
        ballTest.reverseX();
        assertEquals(-2, ballTest.getSpeedX());
    }

    @Test
    void shouldReverseSpeedY() {
        ballTest.setSpeed(2, -3);
        ballTest.reverseY();
        assertEquals(3, ballTest.getSpeedY());
    }

    @Test
    void shouldIncrementSpeed() {
        ballTest.setSpeed(2, 3);
        ballTest.incSpeed();
        assertEquals(2.1, ballTest.getSpeedX(), 0.005);
        assertEquals(3.15, ballTest.getSpeedY(), 0.005);
    }
}