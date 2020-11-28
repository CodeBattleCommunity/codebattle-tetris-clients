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
		/// Размер игровой доски (размер доски Size x Size клеток)
		/// </summary>
		public int Size { get; private set; }

		public Board(string boardString)
		{
			RawBoard = JsonConvert.DeserializeObject<JsonBoard>(boardString.Replace("\n", ""));
			Size = (int)Math.Sqrt(RawBoard.Layers[0].Length);
			LengthXY = new LengthToXY(Size);
		}

		/// <summary>
		/// Получить игровое поле в виде массива символов
		/// </summary>
		/// <returns></returns>
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

		/// <summary>
		/// Получить координаты, на которых распологаются заданные элементы
		/// </summary>
		/// <param name="elements"></param>
		/// <returns></returns>
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

		/// <summary>
		/// Получить элемент по заданной координате
		/// </summary>
		/// <param name="point"></param>
		/// <returns></returns>
		public Element GetAt(Point point)
		{
			if (point.IsOutOf(Size))
			{
				return Element.NONE;
			}
			return GetAtInternal(point.X, point.Y);
		}

		/// <summary>
		/// Получить элемент по заданной координате
		/// </summary>
		/// <param name="x"></param>
		/// <param name="y"></param>
		/// <returns></returns>
		public Element GetAt(int x, int y)
		{
			if (IsOutOfField(x, y))
				throw new Exception("Out of range");
			return GetAtInternal(x, y);
		}

		/// <summary>
		/// Получить все элементы по заданной координате
		/// </summary>
		/// <param name="x"></param>
		/// <param name="y"></param>
		/// <returns></returns>
		public List<Element> GetAllAt(int x, int y)
		{
			if (IsOutOfField(x, y))
				throw new Exception("Out of range");
			return GetAllAtInternal(x, y);
		}

		/// <summary>
		/// Получить все элементы по заданной координате
		/// </summary>
		/// <param name="pt"></param>
		/// <returns></returns>
		public List<Element> GetAllAt(Point pt)
		{
			return GetAllAt(pt.X, pt.Y);
		}

		/// <summary>
		/// Проверить, находится ли хотя бы один из элементов по указанной координате
		/// </summary>
		/// <param name="x"></param>
		/// <param name="y"></param>
		/// <param name="elements"></param>
		/// <returns></returns>
		public bool IsAt(int x, int y, params Element[] elements)
		{
			if (IsOutOfField(x, y))
			{
				return false;
			}
			return elements.Contains(GetAtInternal(x, y));
		}

		/// <summary>
		/// Проверить, находится ли хотя бы один из элементов по указанной координате
		/// </summary>
		/// <param name="point"></param>
		/// <param name="elements"></param>
		/// <returns></returns>
		public bool IsAt(Point point, params Element[] elements)
		{
			if (point.IsOutOf(Size))
			{
				return false;
			}
			return elements.Contains(GetAt(point));
		}

		/// <summary>
		/// Проверить, находится ли элемент по соседству с указанной координатой. Проверяются также угловые соседи.
		/// </summary>
		/// <param name="x"></param>
		/// <param name="y"></param>
		/// <param name="element"></param>
		/// <returns></returns>
		public bool IsNear(int x, int y, Element element)
		{
			return CountNear(x, y, element) > 0;
		}

		/// <summary>
		/// Проверить, находится ли элемент по соседству с указанной координатой. Проверяются также угловые соседи.
		/// </summary>
		/// <param name="pt"></param>
		/// <param name="element"></param>
		/// <returns></returns>
		public bool IsNear(Point pt, Element element)
		{
			return IsNear(pt.X, pt.Y, element);
		}

		/// <summary>
		/// Подсчитать количество соседних клеток, являющихся заданным элементом.
		/// </summary>
		/// <param name="x"></param>
		/// <param name="y"></param>
		/// <param name="element"></param>
		/// <returns></returns>
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

		/// <summary>
		/// Подсчитать количество соседних клеток, являющихся заданным элементом.
		/// </summary>
		/// <param name="pt"></param>
		/// <param name="element"></param>
		/// <returns></returns>
		public int CountNear(Point pt, Element element)
		{
			return CountNear(pt.X, pt.Y, element);
		}

		/// <summary>
		/// Конвертирует символ в элемент перечисления Element
		/// </summary>
		/// <param name="ch"></param>
		/// <returns></returns>
		public Element ValueOf(char ch)
		{
			return (Element)ch;
		}


		/// <summary>
		/// Получить все элементы на соседних клетках
		/// </summary>
		/// <param name="x"></param>
		/// <param name="y"></param>
		/// <returns></returns>
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

		/// <summary>
		/// Получить все элементы на соседних клетках
		/// </summary>
		/// <param name="point"></param>
		/// <returns></returns>
		public List<Element> GetNear(Point point)
		{
			return GetNear(point.X, point.Y);
		}

		/// <summary>
		/// Проверить, выходит ли точка за пределы игрового поля
		/// </summary>
		/// <param name="x"></param>
		/// <param name="y"></param>
		/// <returns></returns>
		public bool IsOutOfField(int x, int y)
		{
			return x < 0 || x >= Size || y < 0 || y >= Size;
		}

		/// <summary>
		/// Получить все клетки, содержащие фигуры
		/// </summary>
		/// <returns></returns>
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

		/// <summary>
		/// Получить клетки, не занятые фигурами.
		/// </summary>
		/// <returns></returns>
		public List<Point> GetFreeSpace()
		{
			return Get(Element.NONE);
		}

		/// <summary>
		/// Проверить, является ли клетка пустой
		/// </summary>
		/// <param name="x"></param>
		/// <param name="y"></param>
		/// <returns></returns>
		public bool IsFree(int x, int y)
		{
			return GetAtInternal(x, y) == Element.NONE;
		}

		/// <summary>
		/// Получить тип текущей фигуры
		/// </summary>
		/// <returns></returns>
		public Element GetCurrentFigureType()
		{
			return ValueOf(RawBoard.CurrentFigureType);
		}

		/// <summary>
		/// Получить координату текущей фигуры
		/// </summary>
		/// <returns></returns>
		public Point GetCurrentFigurePoint()
		{
			return RawBoard.CurrentFigurePoint;
		}

		/// <summary>
		/// Получить следующие фигуры
		/// </summary>
		/// <returns></returns>
		public List<Element> GetFutureFigures()
		{
			return RawBoard.FutureFigures.Select(x => ValueOf(x)).ToList();
		}

		/// <summary>
		/// Перезаписать значение клетки другим значением (не влияет на состояние сервера)
		/// </summary>
		/// <param name="x"></param>
		/// <param name="y"></param>
		/// <param name="ch"></param>
		public void Set(int x, int y, char ch)
		{
			char[] oldLayer = RawBoard.Layers[0].ToCharArray();
			oldLayer[LengthXY.GetLength(x, y)] = ch;
			RawBoard.Layers[0] = new string(oldLayer);
		}

		/// <summary>
		/// Инвертирует значение Y-координаты
		/// </summary>
		/// <param name="y"></param>
		/// <returns></returns>
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


		#region Экспериментальные методы. Использовать осторожно!

		/// <summary>
		/// Позволяет определить координаты точек текущей фигуры после применения операции вращения к ней.
		/// Поворот производится от исходного положения фигуры (в котором она появляется в стакане)!
		/// </summary>
		/// <param name="rotation">Поворот фигуры по часовой стрелке.</param>
		/// <returns>Набор точек текущей фигуры с координатами после вращения данной фигуры.</returns>
		public Point[] PredictCurrentFigurePoints(Rotation rotation = Rotation.CLOCKWIZE_0)
		{
			return FigureRotator.PredictCurrentFigurePoints(rotation, GetCurrentFigurePoint(), GetCurrentFigureType());
		}

		/// <summary>
		/// Позволяет определить координаты точек текущей фигуры после применения операции вращения к ней.
		/// Поворот производится от исходного положения фигуры (в котором она появляется в стакане)!
		/// </summary>
		/// <param name="figureAnchor">Точка - "якорь" фигуры.</param>
		/// <param name="rotation">Поворот фигуры по часовой стрелке.</param>
		/// <returns>Набор точек текущей фигуры с координатами после вращения данной фигуры.</returns>
		public Point[] PredictCurrentFigurePoints(Point figureAnchor, Rotation rotation = Rotation.CLOCKWIZE_0)
		{
			return FigureRotator.PredictCurrentFigurePoints(rotation, figureAnchor, GetCurrentFigureType());
		}

		#endregion

		/// <summary>
		/// Получить строковое представление доски
		/// </summary>
		/// <returns></returns>
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
