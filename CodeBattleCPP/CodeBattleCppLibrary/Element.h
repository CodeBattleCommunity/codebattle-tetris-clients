#pragma once

#include <cstdint>
#include <iostream>
enum class Element : char {
    BLUE = 'I',
    CYAN = 'J',
    ORANGE = 'L',
    YELLOW = 'O',
    GREEN = 'S',
    PURPLE = 'T',
    RED = 'Z',
    NONE = '.',
};

inline std::ostream& operator<<(std::ostream& os, Element& elem)
{
    switch (elem) {
    case Element::BLUE:
        os << "I";
        break;
    case Element::CYAN:
        os << "J";
        break;
    case Element::ORANGE:
        os << "L";
        break;
    case Element::YELLOW:
        os << "O";
        break;
    case Element::GREEN:
        os << "S";
        break;
    case Element::PURPLE:
        os << "T";
        break;
    case Element::RED:
        os << "Z";
        break;
    case Element::NONE:
        os << ".";
        break;
    }
    return os;
}

