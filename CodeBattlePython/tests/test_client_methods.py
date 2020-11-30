# you can run the tests with pytest

from tetris_client.internals.element import get_shift_after_rotation, Element
from tetris_client.internals.point import Point


def test_get_shift_after_rotation():
    expected = "shift_left"
    degrees = 270
    rotation = int(degrees / 90)
    assert get_shift_after_rotation("shift_top", rotation) == expected

    expected = "shift_top"
    degrees = 360
    rotation = int(degrees / 90)
    assert get_shift_after_rotation("shift_top", rotation) == expected

    expected = "shift_right"
    degrees = 450
    rotation = int(degrees / 90)
    assert get_shift_after_rotation("shift_top", rotation) == expected

    expected = "shift_top"
    degrees = 720
    rotation = int(degrees / 90)
    assert get_shift_after_rotation("shift_top", rotation) == expected

    expected = "shift_bottom"
    degrees = 900
    rotation = int(degrees / 90)
    assert get_shift_after_rotation("shift_top", rotation) == expected


def test_element_rotation_i():
    # blue I 
    element = Element('I')
    # точка вращения
    anchor = Point(8, 16)
    expected = [Point(8, 17), Point(8, 16), Point(8, 15), Point(8, 14)]
    assert expected == element.get_all_coords(anchor, rotation=0)
    expected = [Point(9, 16), Point(8, 16), Point(7, 16), Point(6, 16)]
    assert expected == element.get_all_coords(anchor, rotation=1)


def test_element_rotation_o():
    # blue I 
    element = Element('O')
    # anchor - точка вращения
    anchor = Point(8,17)
    expected = [Point(8,17), Point(9,17), Point(8,16), Point(9,16)]
    assert expected == element.get_all_coords(anchor, rotation=0)
    expected = [Point(8,17), Point(8,16), Point(7,17), Point(7,16)]
    assert expected == element.get_all_coords(anchor, rotation=1)