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

const D = (index, dx, dy, name, next) => {
  const combine = (direction) => D(index, 0, 0, toString(), direction);

  const changeX = (x) => x + dx;

  const changeY = (y) => y - dy;

  const toString = () => (next ? name + ", " + next.toString() : name);

  const inverted = function () {
    switch (this) {
      case DIRECTIONS.UP:
        return DIRECTIONS.DOWN;
      case DIRECTIONS.DOWN:
        return DIRECTIONS.UP;
      case DIRECTIONS.LEFT:
        return DIRECTIONS.RIGHT;
      case DIRECTIONS.RIGHT:
        return DIRECTIONS.LEFT;
      case DIRECTIONS.ROTATE_270:
        return DIRECTIONS.ROTATE_90;
      case DIRECTIONS.ROTATE_90:
        return DIRECTIONS.ROTATE_270;
      case DIRECTIONS.STOP:
        return DIRECTIONS.ROTATE_180;
      default:
        return DIRECTIONS.STOP;
    }
  };

  const getIndex = () => index;

  return {
    combine,
    changeX,
    changeY,
    inverted,
    toString,
    getIndex,
  };
};

const DIRECTIONS = {
  DOWN: D(0, 0, 1, "DOWN"),
  LEFT: D(1, -1, 0, "LEFT"),
  RIGHT: D(2, 1, 0, "RIGHT"),
  STOP: D(5, 0, 0, ""),
  ROTATE_90: D(6, 0, 0, "act"),
  ROTATE_180: D(7, 0, 0, "act(2)"),
  ROTATE_270: D(8, 0, 0, "act(3)"),
};

DIRECTIONS.values = function () {
  return [
    DIRECTIONS.DOWN,
    DIRECTIONS.LEFT,
    DIRECTIONS.RIGHT,
    DIRECTIONS.ROTATE_90,
    DIRECTIONS.ROTATE_180,
    DIRECTIONS.ROTATE_270,
  ];
};

DIRECTIONS.valueOf = (index) =>
  DIRECTIONS.values().find((direction) => direction.getIndex() === index);
