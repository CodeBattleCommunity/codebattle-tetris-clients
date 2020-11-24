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

class LengthToXY {
  constructor(boardSize) {
    this.boardSize = boardSize;
  }
  inversionY(y) {
    return this.boardSize - 1 - y;
  }

  getXY(length, element) {
    return length == -1
      ? null
      : new Point(
          length % this.boardSize,
          this.inversionY(Math.trunc(length / this.boardSize)),
          element || null
        );
  }

  getLength(x, y) {
    return this.inversionY(y) * this.boardSize + x;
  }
}

class Board {
  constructor(board) {
    this.board = board;
    this.boardSize = this.size() * this.size();
    this.lengthToXY = new LengthToXY(this.size());
    this.isAt = this.isAt.bind(this);
  }

  getAt(x, y) {
    return this.pt(x, y).isOutOf(this.size())
      ? Element.BORDER
      : this.board.layers[0].charAt(this.lengthToXY.getLength(x, y));
  }

  pt(x, y) {
    return new Point(x, y);
  }

  isAt(x, y, element) {
    return this.getAt(x, y) == element ? true : false;
  }

  get(elements) {
    let result = [];
    for (let i = 0; i < this.boardSize; i++) {
      const point = this.lengthToXY.getXY(i);
      const isElementInPoint = elements.some((element) =>
        this.isAt(point.getX(), point.getY(), element)
      );
      if (isElementInPoint) {
        result.push(point);
      }
    }
    return result;
  }

  getFigures() {
    return this.get([
        ELEMENTS.BLUE,
        ELEMENTS.CYAN,
        ELEMENTS.ORANGE,
        ELEMENTS.YELLOW,
        ELEMENTS.GREEN,
        ELEMENTS.PURPLE,
        ELEMENTS.RED,
    ]);
  }

  getFreeSpace() {
    return this.get([ELEMENTS.NONE]);
  }

  getCurrentFigureType() {
    return this.board.currentFigureType;
  }

  getCurrentFigurePosition() {
    const x = this.board.currentFigurePoint.x;
    const y = this.board.currentFigurePoint.y;
    return new Point(x, y, this.getAt(x, y));
  }

  getFutureFigures() {
    return this.board.futureFigures;
  }

  isNear(x, y, element) {
    return !this.pt(x, y).isOutOf(this.size())
      ? this.isAt(x + 1, y, element) ||
          this.isAt(x - 1, y, element) ||
          this.isAt(x, y + 1, element) ||
          this.isAt(x, y - 1, element)
      : false;
  }

  size() {
    return Math.sqrt(this.board.layers[0].length);
  }

  getAllExtended() {
    let result = [];
    for (let i = 0; i < this.boardSize; i++) {
      var point = this.lengthToXY.getXY(i, this.board.layers[0].charAt(i));
      result.push(point);
    }
    return result;
  }

  toString() {
    const currentFigurePoint = this.board.currentFigurePoint;
    const size = this.size();
    let result = `Current Figure Point: [${currentFigurePoint.x};${currentFigurePoint.y}]\nCurrent Figure Type: ${this.board.currentFigureType}\nFuture Figures:: ${this.board.futureFigures}\n`;

    for (var i = 0; i < size; i++) {
      result += this.board.layers[0].substring(i * size, (i + 1) * size);
      result += "\n";
    }
    return result;
  }
}
