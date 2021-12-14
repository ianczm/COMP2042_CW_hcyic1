package com.hcyic1.brickdestroy.model.platform;

import com.hcyic1.brickdestroy.model.ball.RubberBall;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Point2D;

class PlatformTest {

    private final Rectangle drawArea = new Rectangle(0, 0, 600, 450);
    private final Platform platform = new Platform(new Point(25, 20), 150, 10, drawArea);
    private final Point2D center = new Point(25, 25);
    private final RubberBall ball = new RubberBall(center);

    @Test
    void shouldMoveTo() {
        Point newPosition = new Point(50, 300);
        platform.moveTo(newPosition);
        Point centreOfResult = platform.getPlatformFace().getBounds().getLocation();
        centreOfResult.x += 75; // correcting for off-centre calculation
        Assertions.assertEquals(newPosition, centreOfResult);
    }

    @Test
    void shouldNotRecordImpact() {
        boolean res = platform.impact(ball);
        Assertions.assertFalse(res);
    }
}