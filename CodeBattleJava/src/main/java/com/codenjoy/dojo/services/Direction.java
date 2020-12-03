package com.codenjoy.dojo.services;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2018 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

/**
 * Имплементит возможные направления движения чего либо
 */
public enum Direction {
    UP(0), RIGHT(1), DOWN(2), LEFT(3);

    private final int value;

    Direction(int value) {
        this.value = value;
    }

    public static Direction valueOf(int i) {
        for (Direction d : Direction.values()) {
            if (d.value == i) {
                return d;
            }
        }
        throw new IllegalArgumentException("No such Direction for " + i);
    }

    /**
     * @return Value of this direction.
     */
    public int value() {
        return value;
    }

    /**
     * @return Inverted direction. Inverts UP to DOWN, RIGHT to LEFT, etc.
     */
    public Direction inverted() {
        switch (this) {
        case UP:
            return DOWN;
        case DOWN:
            return UP;
        case LEFT:
            return RIGHT;
        case RIGHT:
            return LEFT;
        default:
            throw new IllegalArgumentException("Unsupported direction");
        }
    }

    /**
     * @return Next clockwise direction. LEFT -> UP -> RIGHT -> DOWN -> LEFT.
     */
    public Direction clockwise() {
        switch (this) {
        case LEFT:
            return UP;
        case UP:
            return RIGHT;
        case RIGHT:
            return DOWN;
        case DOWN:
            return LEFT;
        default:
            throw new IllegalArgumentException("Cant clockwise for: " + this);
        }
    }

}
