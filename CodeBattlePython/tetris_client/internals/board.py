from math import sqrt

from tetris_client.internals.element import Element
from tetris_client.internals.point import Point
from typing import Optional, Tuple, List
import json


class Board:
    """ Class describes the Board field for Bomberman game."""

    def __init__(self, board_string):
        self._string = board_string.replace("\n", "")
        self._json = json.loads(self._string)
        self._layer = self._json["layers"][0]
        self._len = len(self._layer)  # the length of the string
        self._size = int(sqrt(self._len))  # size of the board

    def __str__(self):
        return "Board:\n{brd}".format(brd=self._line_by_line())

    def get_point_by_shift(self, shift: int) -> Point:
        return Point(shift % self._size, shift / self._size)

    def find_first_element(self, *element_types) -> Optional[Point]:
        _result = []
        for i in range(self._size * self._size):
            point = self.get_point_by_shift(i)
            for type_ in element_types:
                if self.has_element_at(point, type_):
                    return point
        return None

    def _find_all(self, *element_types) -> list:
        """ Returns the list of points for the given element type."""
        _points = []
        for i in range(self._size * self._size):
            point = self.get_point_by_shift(i)
            for type_ in element_types:
                if self.has_element_at(point, type_):
                    _points.append(point)
        return _points

    def get_current_figure_point(self) -> Point:
        return Point(
            int(self._json["currentFigurePoint"]["x"]),
            int(self._json["currentFigurePoint"]["y"]),
        )

    def get_current_figure_type(self) -> str:
        return self._json["currentFigureType"]

    def get_future_figures(self) -> List[str]:
        return self._json["futureFigures"]

    def get_element_at(self, point: Point) -> Element:
        """ Return an Element object at coordinates x,y."""
        return Element(self._layer[self._xy2strpos(point.get_x(), point.get_y())])

    def has_element_at(self, point: Point, element_object: Element) -> bool:
        if point.is_out_of_board(self._size):
            return False
        return element_object == self.get_element_at(point)

    def find_element(self, type_: Element) -> Optional[Point]:
        for i in range(self._size * self._size):
            point = self.get_point_by_shift(i)
            if self.has_element_at(point, type_):
                return point
        return None

    def get_shift_by_point(self, point: Point) -> int:
        return point.get_y() * self._size + point.get_x()

    def _strpos2pt(self, strpos: int) -> Point:
        return Point(*self._strpos2xy(strpos))

    def _strpos2xy(self, strpos: int) -> Tuple[int, int]:
        return strpos % self._size, strpos // self._size

    def _xy2strpos(self, x: int, y: int) -> int:
        return self._size * y + x

    def print_board(self):
        print(self._line_by_line())

    def _line_by_line(self) -> str:
        return "\n".join(
            [
                self._json["layers"][0][i : i + self._size]
                for i in range(0, self._len, self._size)
            ]
        )

    def to_string(self) -> str:
        return str(self)


if __name__ == "__main__":
    raise RuntimeError("This module is not designed to be ran from CLI")
