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
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Newtonsoft.Json;

namespace TetrisClient
{
	public class Board
	{
		private JsonBoard RawBoard;
		private LengthToXY LengthXY;

		/// <summary>
		/// GameBoard size (actual board size is MapSize x MapSize cells)
		/// </summary>
		public int Size { get; private set; }

		public Board(string boardString)
		{
			RawBoard = JsonConvert.DeserializeObject<JsonBoard>(boardString.Replace("\n", ""));
			Size = (int)Math.Sqrt(RawBoard.Layers[0].Length);
			LengthXY = new LengthToXY(Size);
		}

		public char[,] GetField()
		{
			char[,] field = new char[Size, Size];
			for (int i = 0; i < Size; i++)
			{
				for (int j = 0; j < Size; j++)
				{
					field[i, j] = RawBoard.Layers[0][LengthXY.GetLength(i, j)];
				}
			}
			return field;
		}

		public List<Point> Get(params Element[] elements)
		{
			List<Point> result = new List<Point>();

			for (int i = 0; i < Size * Size; i++)
			{
				Point pt = LengthXY.GetXY(i);

				if (elements.Contains(GetAtInternal(pt.X, pt.Y)))
				{
					result.Add(pt);
				}
			}

			return result;
		}

		public Element GetAt(Point point)
		{
			if (point.IsOutOf(Size))
			{
				return Element.NONE;
			}
			return GetAtInternal(point.X, point.Y);
		}

		public Element GetAt(int x, int y)
		{
			if (IsOutOfField(x, y))
				throw new Exception("Out of range");
			return GetAtInternal(x, y);
		}

		public List<Element> GetAllAt(int x, int y)
		{
			if (IsOutOfField(x, y))
				throw new Exception("Out of range");
			return GetAllAtInternal(x, y);
		}

		public List<Element> GetAllAt(Point pt)
		{
			return GetAllAt(pt.X, pt.Y);
		}


		public bool IsAt(int x, int y, params Element[] elements)
		{
			if (IsOutOfField(x, y))
			{
				return false;
			}
			return elements.Contains(GetAtInternal(x, y));
		}

		public bool IsAt(Point point, params Element[] elements)
		{
			if (point.IsOutOf(Size))
			{
				return false;
			}
			return elements.Contains(GetAt(point));
		}

		public bool IsNear(int x, int y, Element element)
		{
			return CountNear(x, y, element) > 0;
		}

		public bool IsNear(Point pt, Element element)
		{
			return IsNear(pt.X, pt.Y, element);
		}

		public int CountNear(int x, int y, Element element)
		{
			int count = 0;
			for (int i = x - 1; i < x + 2; i++)
			{
				for (int j = y - 1; j < y + 2; j++)
				{
					if (i == x && j == y)
						continue;
					if (IsAt(i, j, element))
						count++;
				}
			}
			return count;
		}

		public int CountNear(Point pt, Element element)
		{
			return CountNear(pt.X, pt.Y, element);
		}


		public Element ValueOf(char ch)
		{
			return (Element)ch;
		}

		public List<Element> GetNear(int x, int y)
		{
			List<Element> elements = new List<Element>(8);
			for (int i = x - 1; i < x + 2; i++)
			{
				for (int j = y - 1; j < y + 2; j++)
				{
					if (i == x && j == y || IsOutOfField(i, j))
						continue;
					elements.Add(GetAt(i, j));
				}
			}

			return elements;
		}

		public List<Element> GetNear(Point point)
		{
			return GetNear(point.X, point.Y);
		}

		public bool IsOutOfField(int x, int y)
		{
			return x < 0 || x >= Size || y < 0 || y >= Size;
		}

		public List<Point> GetFigures()
		{
			return Get(
				Element.BLUE,
				Element.CYAN,
				Element.ORANGE,
				Element.YELLOW,
				Element.GREEN,
				Element.PURPLE,
				Element.RED
			);
		}

		public List<Point> GetFreeSpace()
		{
			return Get(Element.NONE);
		}

		public bool IsFree(int x, int y)
		{
			return GetAtInternal(x, y) == Element.NONE;
		}

		public Element GetCurrentFigureType()
		{
			return ValueOf(RawBoard.CurrentFigureType);
		}

		public Point GetCurrentFigurePoint()
		{
			return RawBoard.CurrentFigurePoint;
		}

		public List<Element> GetFutureFigures()
		{
			return RawBoard.FutureFigures.Select(x => ValueOf(x)).ToList();
		}

		public void Set(int x, int y, char ch)
		{
			char[] oldLayer = RawBoard.Layers[0].ToCharArray();
			oldLayer[LengthXY.GetLength(x, y)] = ch;
			RawBoard.Layers[0] = new string(oldLayer);
		}

		public int InversionY(int y)
		{
			return Size - 1 - y;
		}

		private Element GetAtInternal(int x, int y)
		{
			return (Element)RawBoard.Layers[0][LengthXY.GetLength(x, y)];
		}

		private List<Element> GetAllAtInternal(int x, int y)
		{
			return RawBoard.Layers.Select(layer => (Element)layer[LengthXY.GetLength(x, y)])
				.ToList();
		}

		public override string ToString()
		{
			StringBuilder sb = new StringBuilder();
			sb.Append("Current Figure Point: ");
			sb.Append(RawBoard.CurrentFigurePoint.ToString());
			sb.AppendLine();
			sb.Append("Current Figure Type: ");
			sb.Append(RawBoard.CurrentFigureType);
			sb.AppendLine();
			sb.Append("Future Figures: ");
			RawBoard.FutureFigures.ForEach(x => sb.Append(x));
			sb.AppendLine();
			for (int line = 0; line < Size; line++)
			{
				if (line > 0)
					sb.AppendLine();
				sb.Append("  ");
				sb.Append(RawBoard.Layers[0].Substring(Size * line, Size));
			}

			return sb.ToString();
		}
	}
}
