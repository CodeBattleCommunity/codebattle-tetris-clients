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
