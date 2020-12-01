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
class Solver {
  constructor(DIRECTIONS, ELEMENTS) {
    this.directions = DIRECTIONS;
    this.elements = ELEMENTS;
    this.actions = [];
  }

  /**
   * 
   * @param {string} direction value of this.directions
   * @param {number} count actions count
   * @example `addActionStep(this.directions.RIGHT, 3)` will add 'RIGHT, RIGHT, RIGHT' to the result
  */

  addActionStep(direction, count = 1) {
    this.actions = [...this.actions, ...Array(count).fill(direction)];
  }

  /**
    * @return string with action steps divided by comma with space
  */
  
  get(board) {
    // TODO your code here
    return this.actions.join(', ');
  }
}
