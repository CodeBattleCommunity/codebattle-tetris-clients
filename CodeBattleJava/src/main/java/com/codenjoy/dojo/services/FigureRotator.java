package com.codenjoy.dojo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import com.codenjoy.dojo.tetris.model.Elements;

import lombok.var;

public class FigureRotator {

    private static List<BiFunction<Point, Integer, Point>> SHIFT_DELEGATES = new ArrayList<>();
    static {
        SHIFT_DELEGATES.add((point, delta) -> point.shiftTop(delta));
        SHIFT_DELEGATES.add((point, delta) -> point.shiftRight(delta));
        SHIFT_DELEGATES.add((point, delta) -> point.shiftBottom(delta));
        SHIFT_DELEGATES.add((point, delta) -> point.shiftLeft(delta));
    };

    public static Point[] predictCurrentFigurePoints(Rotation rotation, Point anchor, Elements figureType) {
        var shiftTopAfterRotation = getShiftAfterRotation(Direction.UP, rotation);
        var shiftRightAfterRotation = getShiftAfterRotation(Direction.RIGHT, rotation);
        var shiftBottomAfterRotation = getShiftAfterRotation(Direction.DOWN, rotation);
        var shiftLeftAfterRotation = getShiftAfterRotation(Direction.LEFT, rotation);

        switch (figureType) {
        case BLUE:
            return new Point[] {
                    shift(anchor, shiftTopAfterRotation, 1),
                    anchor,
                    shift(anchor, shiftBottomAfterRotation, 1),
                    shift(anchor, shiftBottomAfterRotation, 2),
            };
        case CYAN:
            return new Point[] {
                    shift(anchor, shiftTopAfterRotation, 1),
                    anchor,
                    shift(anchor, shiftBottomAfterRotation, 1),
                    shift(shift(anchor, shiftBottomAfterRotation, 1), shiftLeftAfterRotation, 1),
            };
        case ORANGE:
            return new Point[] {
                    shift(anchor, shiftTopAfterRotation, 1),
                    anchor,
                    shift(anchor, shiftBottomAfterRotation, 1),
                    shift(shift(anchor, shiftBottomAfterRotation, 1), shiftRightAfterRotation, 1),
            };
        case YELLOW:
            return new Point[] {
                    anchor,
                    shift(anchor, shiftRightAfterRotation, 1),
                    shift(anchor, shiftBottomAfterRotation, 1),
                    shift(shift(anchor, shiftBottomAfterRotation, 1), shiftRightAfterRotation, 1),
            };
        case GREEN:
            return new Point[] {
                    shift(anchor, shiftLeftAfterRotation, 1),
                    anchor,
                    shift(anchor, shiftTopAfterRotation, 1),
                    shift(shift(anchor, shiftTopAfterRotation, 1), shiftRightAfterRotation, 1),
            };
        case PURPLE:
            return new Point[] {
                    shift(anchor, shiftLeftAfterRotation, 1),
                    anchor,
                    shift(anchor, shiftRightAfterRotation, 1),
                    shift(anchor, shiftTopAfterRotation, 1),
            };
        case RED:
            return new Point[] {
                    shift(shift(anchor, shiftTopAfterRotation, 1), shiftLeftAfterRotation, 1),
                    shift(anchor, shiftTopAfterRotation, 1),
                    anchor,
                    shift(anchor, shiftRightAfterRotation, 1),
            };
        default:
            throw new IllegalArgumentException("Тип фигуры не поддерживается: " + figureType);
        }
    }

    private static BiFunction<Point, Integer, Point> getShiftAfterRotation(Direction shiftDirection,
            Rotation rotation) {
        return SHIFT_DELEGATES.get((shiftDirection.ordinal() + rotation.ordinal()) % 4);
    }

    private static Point shift(Point point, BiFunction<Point, Integer, Point> shiftDelegate, int delta) {
        return shiftDelegate.apply(point, delta);
    }

}
