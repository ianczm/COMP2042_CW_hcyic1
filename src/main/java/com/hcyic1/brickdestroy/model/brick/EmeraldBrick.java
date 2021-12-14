package com.hcyic1.brickdestroy.model.brick;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;

public class EmeraldBrick extends Brick {

    private static final Color DEF_INNER = new Color(75, 175, 75);
    private static final Color DEF_BORDER = Color.BLACK;
    private static final int BRICK_STRENGTH = 3;
    private static final double BREAK_PROBABILITY = 0.2;

    private Random rnd;
    private Shape brickFace;

    public EmeraldBrick(Point point, Dimension size) {
        super(point, size, DEF_BORDER, DEF_INNER, BRICK_STRENGTH);
        rnd = new Random();
        brickFace = super.brickFace;
    }


    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos, size);
    }

    @Override
    public Shape getBrick() {
        return brickFace;
    }

    public boolean setImpact(Point2D point, int dir) {
        if (super.isBroken())
            return false;
        impact();
        return super.isBroken();
    }

    public void impact() {
        if (rnd.nextDouble() < BREAK_PROBABILITY) {
            super.impact();
        }
    }

}
