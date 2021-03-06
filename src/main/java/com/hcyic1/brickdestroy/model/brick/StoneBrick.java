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
package com.hcyic1.brickdestroy.model.brick;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;


public class StoneBrick extends Brick {

    private static final Color DEF_INNER = new Color(147, 147, 147).darker();
    private static final Color DEF_BORDER = Color.BLACK;
    private static final int BRICK_STRENGTH = 1;
    private static final double BREAK_PROBABILITY = 0.4;

    private Random rnd;
    private Shape brickFace;

    public StoneBrick(Point point, Dimension size) {
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
