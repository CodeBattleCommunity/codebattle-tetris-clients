from tetris_client.internals.element import Element
from tetris_client.internals.point import Point
from typing import Tuple, Union
import logging

logger = logging.getLogger(__name__)


def prepare_element(element_object: Union[Element, str]) -> Point:
    try: 
        print(type(element_object))
        if not isinstance(element_object, Element):
            element_object = Element(element_object)
    except Exception as e:
        logger.info("element_object argument must be a type of Element or String with element name or code")
        raise e
    return element_object 


def prepare_point(point: Union[Point, Tuple[int]]) -> Point:
    try: 
        if not isinstance(point, Point):
            point = Point(point[0], point[1])
    except Exception as e:
        logger.info("point argument must be a type of Point or Tuple with coordinats integers")
        raise e
    return point