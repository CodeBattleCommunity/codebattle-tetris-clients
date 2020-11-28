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
using System;

namespace TetrisClient
{
	public static class FigureRotator
	{
		private static Func<Point, int, Point>[] ShiftDelegates = new Func<Point, int, Point>[]
		{
			(point, delta) => point.ShiftTop(delta),
			(point, delta) => point.ShiftRight(delta),
			(point, delta) => point.ShiftBottom(delta),
			(point, delta) => point.ShiftLeft(delta),
		};

		public static Point[] PredictCurrentFigurePoints(Rotation rotation, Point anchor, Element figureType)
		{
			var shiftTopAfterRotation = GetShiftAfterRotation(Direction.Up, rotation);
			var shiftRightAfterRotation = GetShiftAfterRotation(Direction.Right, rotation);
			var shiftBottomAfterRotation = GetShiftAfterRotation(Direction.Down, rotation);
			var shiftLeftAfterRotation = GetShiftAfterRotation(Direction.Left, rotation);

			switch (figureType)
			{
				case Element.BLUE:
					return new[]
					{
						anchor.Shift(shiftTopAfterRotation, 1),
						anchor,
						anchor.Shift(shiftBottomAfterRotation, 1),
						anchor.Shift(shiftBottomAfterRotation, 2),
					};
				case Element.CYAN:
					return new[]
					{
						anchor.Shift(shiftTopAfterRotation, 1),
						anchor,
						anchor.Shift(shiftBottomAfterRotation, 1),
						anchor.Shift(shiftBottomAfterRotation, 1)
							  .Shift(shiftLeftAfterRotation, 1),
					};
				case Element.ORANGE:
					return new[]
					{
						anchor.Shift(shiftTopAfterRotation, 1),
						anchor,
						anchor.Shift(shiftBottomAfterRotation, 1),
						anchor.Shift(shiftBottomAfterRotation, 1)
							  .Shift(shiftRightAfterRotation, 1),
					};
				case Element.YELLOW:
					return new[]
					{
						anchor,
						anchor.Shift(shiftRightAfterRotation, 1),
						anchor.Shift(shiftBottomAfterRotation, 1),
						anchor.Shift(shiftBottomAfterRotation, 1)
							  .Shift(shiftRightAfterRotation, 1),
					};
				case Element.GREEN:
					return new[]
					{
						anchor.Shift(shiftLeftAfterRotation, 1),
						anchor,
						anchor.Shift(shiftTopAfterRotation, 1),
						anchor.Shift(shiftTopAfterRotation, 1)
							  .Shift(shiftRightAfterRotation, 1),
					};
				case Element.PURPLE:
					return new[]
					{
						anchor.Shift(shiftLeftAfterRotation, 1),
						anchor,
						anchor.Shift(shiftRightAfterRotation, 1),
						anchor.Shift(shiftTopAfterRotation, 1),
					};
				case Element.RED:
					return new[]
					{
						anchor.Shift(shiftTopAfterRotation, 1)
							  .Shift(shiftLeftAfterRotation, 1),
						anchor.Shift(shiftTopAfterRotation, 1),
						anchor,
						anchor.Shift(shiftRightAfterRotation, 1),
					};
				default:
					throw new ArgumentException("Тип фигуры не поддерживается", nameof(figureType));
			}
		}

		private static Func<Point, int, Point> GetShiftAfterRotation(Direction shiftDirection, Rotation rotation)
		{
			return ShiftDelegates[((int)shiftDirection + (int)rotation) % 4];
		}

		private static Point Shift(this Point point, Func<Point, int, Point> shiftDelegate, int delta)
		{
			return shiftDelegate(point, delta);
		}
	}
}