/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2018 - 2020 Codenjoy
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
var D = function (index, dx, dy, name, next) {

    var combine = function(direction){
        return D(index, 0, 0, toString() , direction);
    };

    var changeX = function (x) {
        return x + dx;
    };

    var changeY = function (y) {
        return y - dy;
    };

    var inverted = function () {
        switch (this) {
            case Direction.UP: return Direction.DOWN;
            case Direction.DOWN: return Direction.UP;
            case Direction.LEFT: return Direction.RIGHT;
            case Direction.RIGHT: return Direction.LEFT;
            case Direction.ROTATE_270: return Direction.ROTATE_90;
            case Direction.ROTATE_90: return Direction.ROTATE_270;
            case Direction.STOP: return Direction.ROTATE_180;
            default: return Direction.STOP;
        }
    };

    var toString = function () {
        var result = name;
        if(next) result = result + ', ' + next.toString();
        return result;
    };

    return {
        changeX: changeX,

        changeY: changeY,

        inverted: inverted,

        toString: toString,

        getIndex: function () {
            return index;
        },
        combine: combine
    };
};

var Direction = {
    UP: D(2, 0, -1, 'up'),                 // you can move
    DOWN: D(3, 0, 1, 'down'),
    LEFT: D(0, -1, 0, 'left'),
    RIGHT: D(1, 1, 0, 'right'),
    STOP: D(5, 0, 0, ''),                   // stay
    ROTATE_90: D(6, 0, 0, 'act'),
    ROTATE_180: D(7, 0, 0, 'act(2)'),
    ROTATE_270: D(8, 0, 0, 'act(3)'),
    SUICIDE:    D(9, 0, 0, 'act(1,1)'),                // drop bomb

};

Direction.values = function () {
    return [Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT, Direction.STOP, Direction.ROTATE_90, Direction.ROTATE_180, Direction.ROTATE_270, Direction.SUICIDE];
};

Direction.valueOf = function (index) {
    var directions = Direction.values();
    for (var i in directions) {
        var direction = directions[i];
        if (direction.getIndex() == index) {
            return direction;
        }
    }
    return Direction.STOP;
};


if (module) module.exports = Direction;
