package com.hcyic1.brickdestroy.model.brick;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class GraniteBrickTest {

    private final GraniteBrick testBrick = new GraniteBrick(new Point(20, 20), new Dimension(30, 10));
    private final Rectangle testBrickFace = new Rectangle(50, 50, 40, 20);

    @Test
    void shouldMakeGraniteBrickFace() {
        Rectangle newBrickFace = (Rectangle) testBrick.makeBrickFace(new Point(50, 50), new Dimension (40, 20));
        assertEquals(newBrickFace, testBrickFace);
    }

    @Test
    void shouldRepairCracks() {
        testBrick.repair();
        assertFalse(testBrick.isBroken());
    }
}