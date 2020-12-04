from typing import Tuple


class PlayerLevel:
    """ Describes current player level """

    def __init__(self, current, last_passed, total) -> None:
        self._current = current
        self._last_passed = last_passed
        self._total = total

    def __key(self) -> Tuple[int, int, int]:
        return self._current, self._last_passed, self._total

    def to_string(self) -> str:
        return '[current={}; last_passed={}; total={}]'.format(self._current, self._last_passed, self._total)

    def __str__(self) -> str:
        return self.to_string()

    def __repr__(self) -> str:
        return self.to_string()

    def __eq__(self, other) -> bool:
        return self.__key() == other.__key()

    def __hash__(self) -> int:
        return hash(self.__key())

    def get_current(self) -> int:
        return self._current

    def get_last_passed(self) -> int:
        return self._last_passed

    def get_total(self) -> int:
        return self._total
