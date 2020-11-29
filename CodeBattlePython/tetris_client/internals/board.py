from math import sqrt

from tetris_client.internals.element import Element, prepare_element
from tetris_client.internals.point import Point, prepare_point
from typing import Optional, Tuple, List, Union, Text
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
                if self.is_element_at(point, type_):
                    return point
        return None

    def _find_all(self, *element_types) -> list:
        """ Returns the list of points for the given element type."""
        _points = []
        for i in range(self._size * self._size):
            point = self.get_point_by_shift(i)
            for type_ in element_types:
                if self.is_element_at(point, type_):
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

    def get_current_element(self) -> Element:
        return Element(self._json["currentFigureType"])

    def get_element_at(self, point: Union[Point, Tuple[int]]) -> Element:
        """ Return an Element object at coordinates x,y."""
        point = prepare_point(point)
        return Element(self._layer[self._xy2strpos(point.get_x(), point.get_y())])

    def is_element_at(
        self, point: Union[Point, Tuple[int]], element_object: Union[Element, str]
    ) -> bool:
        point = prepare_point(point)
        element_object
        if point.is_out_of_board(self._size):
            return False
        return element_object == self.get_element_at(point)

    def find_element(self, type_: Union[Element, str]) -> Optional[Point]:
        type_ = prepare_element(type_)
        for i in range(self._size * self._size):
            point = self.get_point_by_shift(i)
            if self.is_element_at(point, type_):
                return point
        return None

    def get_shift_by_point(self, point: Union[Point, Tuple[int]]) -> int:
        point = prepare_point(point)
        return point.get_y() * self._size + point.get_x()

    def predict_figure_points_after_rotation(
        self,
        x: int = None,
        y: int = None,
        figure: Union[Element, Text] = None,
        rotation: int = 0
    ):
        # x, y - координата приходящая с сервера в 'currentFigurePoint' содержит координату новой фигурки.
        # [0, 0] - левый нижний угол фигуры
        # be aware: this method is experemental and can cause issues in edge cases
        # метод возвращает все точки Фигуры при rotation (1 == 90 градусам)
        # подробнее про rotation в методе get_shift_after_rotation
        if not x or not y or not figure:
            anchor: Point = self.get_current_figure_point()
            figure: Element = self.get_current_element()
        else:
            anchor: Point = Point(x, y)
            figure: Element = prepare_element(type_)

        return figure.get_all_coords_after_rotation(anchor, rotation)

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
