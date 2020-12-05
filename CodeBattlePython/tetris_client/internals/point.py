from typing import Tuple, Union
import logging

logger = logging.getLogger(__name__)


class Point:
    """ Describes a point on board."""

    def __init__(self, x=0, y=0):
        self._x = int(x)
        self._y = int(y)

    def __key(self) -> Tuple[int, int]:
        return self._x, self._y

    def __str__(self):
        return self.to_string()

    def __repr__(self):
        return self.to_string()

    def __eq__(self, other_point):
        return self.__key() == other_point.__key()

    def __hash__(self):
        return hash(self.__key())

    def get_x(self) -> int:
        return self._x

    def get_y(self) -> int:
        return self._y

    def to_string(self) -> str:
        return "[{},{}]".format(self._x, self._y)

    # Returns new BoardPoint object shifted bottom "delta" points
    def shift_bottom(self, delta: int = 1):
        return Point(self._x, self._y - delta)

    # Returns new BoardPoint object shifted top "delta" points
    def shift_top(self, delta: int = 1):
        return Point(self._x, self._y + delta)

    # Returns new BoardPoint object shifted right to "delta" pointst
    def shift_right(self, delta: int = 1):
        return Point(self._x + delta, self._y)

    # Returns new BoardPoint object shifted left to "delta" points
    def shift_left(self, delta: int = 1):
        return Point(self._x - delta, self._y)

    # Checks is current point on board or out of range.
    # @param boardSize Board size to compare
    def is_out_of_board(self, board_size: int = 18):
        return (
            self._x >= board_size or self._y >= board_size or self._x < 0 or self._y < 0
        )


def prepare_point(point: Union[Point, Tuple[int]]) -> Point:
    try:
        if not isinstance(point, Point):
            point = Point(point[0], point[1])
    except Exception as e:
        logger.info(
            "point argument must be a type of Point or Tuple with coordinats integers"
        )
        raise e
    return point
