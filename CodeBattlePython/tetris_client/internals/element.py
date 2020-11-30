from tetris_client.internals.point import Point, prepare_point
from typing import List, Text, Union, Tuple
import logging

logger = logging.getLogger(__name__)


_ELEMENTS = dict(
    NONE=".",  # пустое место
    YELLOW="O",
    BLUE="I",
    CYAN="J",
    ORANGE="L",
    GREEN="S",
    RED="Z",
    PURPLE="T",
)

SHIFT_ORDER = ["shift_top", "shift_right", "shift_bottom", "shift_left"]


def get_shift_after_rotation(shift_type: Text = "shift_top", rotation: int = 0) -> Text:
    # rotation - int сколько раз поворачиваем на 90 градусов, на 0 градусов - rotation = 0,
    # на 90 - rotation = 1 , на 180 - rotation = 2, 720 градусов - rotation = 8  и тд
    if not rotation:
        return shift_type
    shift_order = SHIFT_ORDER.index(shift_type)
    if rotation >= len(SHIFT_ORDER) - 1:
        rotation = rotation % len(SHIFT_ORDER)
    shift_order_after_rotation = (shift_order + rotation) % len(SHIFT_ORDER)
    return SHIFT_ORDER[shift_order_after_rotation]


def value_of(char: str) -> str:
    """ Test whether the char is valid Element and return it's name."""
    for value, c in _ELEMENTS.items():
        if char == c:
            return value
    else:
        raise AttributeError("No such Element: {}".format(char))


class Element:
    """ Class describes the Element objects for Bomberman game."""

    def __init__(self, n_or_c: str):
        """ Construct an Element object from given name or char."""
        for n, c in _ELEMENTS.items():
            if n_or_c == n or n_or_c == c:
                self._name = n
                self._char = c
                break
        else:
            raise AttributeError("No such Element: {}".format(n_or_c))

    def get_char(self) -> str:
        """ Return the Element's character."""
        return self._char

    def __eq__(self, otherElement) -> bool:
        return self._name == otherElement._name and self._char == otherElement._char

    def get_all_coords_after_rotation(
        self, anchor: Union[Point, Tuple[int]], rotation: int = 0
    ) -> List[Point]:
        # anchor - левый нижний угол фигуры, аналогично приходящей с сервера в 'currentFigurePoint' координате
        # эксперементальный метод
        # возвращает все координаты фигуры - rotation: 0
        # возвращает координаты фигуры после вращения (rotation = 1, rotation = 2, и т.д)
        # чтобы понять лучше как работает данный метод посмотри тесты - CodeBattlePython/tests/test_client_methods.py
        anchor = prepare_point(anchor)
        shift_top_after_rotation = get_shift_after_rotation("shift_top", rotation)
        shift_right_after_rotation = get_shift_after_rotation("shift_right", rotation)
        shift_bottom_after_rotation = get_shift_after_rotation("shift_bottom", rotation)
        shift_left_after_rotation = get_shift_after_rotation("shift_left", rotation)

        elems_shift = {
            "BLUE": [
                getattr(anchor, shift_top_after_rotation)(1),
                anchor,
                getattr(anchor, shift_bottom_after_rotation)(1),
                getattr(anchor, shift_bottom_after_rotation)(2),
            ],
            "CYAN": [
                getattr(anchor, shift_top_after_rotation)(1),
                anchor,
                getattr(anchor, shift_bottom_after_rotation)(1),
                getattr(
                    getattr(anchor, shift_bottom_after_rotation)(1),
                    shift_left_after_rotation,
                )(1),
            ],
            "ORANGE": [
                getattr(anchor, shift_top_after_rotation)(1),
                anchor,
                getattr(anchor, shift_bottom_after_rotation)(1),
                getattr(
                    getattr(anchor, shift_bottom_after_rotation)(1),
                    shift_right_after_rotation,
                )(1),
            ],
            "YELLOW": [
                anchor,
                getattr(anchor, shift_right_after_rotation)(1),
                getattr(anchor, shift_bottom_after_rotation)(1),
                getattr(
                    getattr(anchor, shift_bottom_after_rotation)(1),
                    shift_right_after_rotation,
                )(1),
            ],
            "GREEN": [
                getattr(anchor, shift_left_after_rotation)(1),
                anchor,
                getattr(anchor, shift_top_after_rotation)(1),
                getattr(
                    getattr(anchor, shift_top_after_rotation)(1),
                    shift_right_after_rotation,
                )(1),
            ],
            "PURPLE": [
                getattr(anchor, shift_left_after_rotation)(1),
                anchor,
                getattr(anchor, shift_right_after_rotation)(1),
                getattr(anchor, shift_top_after_rotation)(1),
            ],
            "RED": [
                getattr(
                    getattr(anchor, shift_top_after_rotation)(1),
                    shift_left_after_rotation,
                )(1),
                getattr(anchor, shift_top_after_rotation)(1),
                anchor,
                getattr(anchor, shift_right_after_rotation)(1),
            ],
        }

        return elems_shift[self._name]


def prepare_element(element_object: Union[Element, str]) -> Point:
    try:
        if not isinstance(element_object, Element):
            element_object = Element(element_object)
    except Exception as e:
        logger.info(
            "element_object argument must be a type of Element or String with element name or code"
        )
        raise e
    return element_object
