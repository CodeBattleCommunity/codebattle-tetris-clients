from tetris_client import GameClient
import random
import logging
from typing import Text
from tetris_client import TetrisAction
from tetris_client import Board
from tetris_client import Element

logging.basicConfig(format="%(asctime)s %(levelname)s:%(message)s", level=logging.INFO)


def turn(gcb: Board) -> TetrisAction:
    # this function must return list actions from TetrisAction: tetris_client/internals/tetris_action.py
    #     LEFT = 'left'
    #     RIGHT = 'right'
    #     DOWN = 'down'
    #     ACT = 'act'
    #     ACT_2 = 'act(2)'
    #     ACT_3 = 'act(3),'
    #     ACT_0_0 = 'act(0,0)'
    # change return below to your code (right now its returns random aciton):
    # код ниже является примером и сэмплом для демонстрации - после подстановки корректного URI к своей игре
    # запустите клиент и посмотрите как отображаются изменения в UI игры и что приходит как ответ от API
    elem = gcb.get_current_figure_type()
    print(gcb.get_future_figures())
    print(gcb.get_current_figure_point())
    print(gcb.get_current_figure_type())
    print(gcb.find_element(elem))
    # predict_figure_points_after_rotation - предсказывает положение фигуры после вращения
    print('rotate prediction: ', gcb.predict_figure_points_after_rotation(rotation=3))
    actions = [x for x in TetrisAction if x.value != "act(0,0)"]
    # return [TetrisAction.LEFT] - example how to send only one action, list with 1 element
    return [
        TetrisAction.LEFT,
        random.choice(actions),
        random.choice(actions),
    ]  # это те действия, которые выполнятся на игровом сервере в качестве вашего хода


def main(uri: Text):
    """
    uri: url for codebattle game
    """
    gcb = GameClient(uri)
    gcb.run(turn)


if __name__ == "__main__":
    # в uri переменную необходимо поместить url с игрой для своего пользователя
    # put your game url in the 'uri' path 1-to-1 as you get in UI
    uri = "http://codebattle2020.westeurope.cloudapp.azure.com/codenjoy-contest/board/player/9r84saxen4c3whdvqfhx?code=3106303325539433635&gameName=tetris"
    main(uri)
