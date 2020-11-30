import logging
import websocket

from tetris_client.internals.board import Board
from tetris_client.internals.tetris_action import TetrisAction

logger = logging.getLogger(__name__)


def sample(ws):
    pass


class GameClient:
    def __init__(self, url):
        path = url.replace("http", "ws")
        path = path.replace("board/player/", "ws?user=")
        path = path.replace("?code=", "&code=")
        logger.info("connecting... {}".format(path))
        websocket.enableTrace(True)
        self.socket = websocket.WebSocketApp(
            path,
            on_message=lambda ws, msg: self.on_message(ws, msg),
            on_error=lambda ws, err: self.on_error(ws, err),
            on_close=lambda ws: self.on_close(ws),
            on_open=lambda ws: self.on_open(ws),
        )

    def run(self, on_turn=lambda a: [TetrisAction.DOWN]):
        self.on_turn = on_turn
        self.socket.run_forever()

    def on_message(self, ws, message):
        board = Board(message.lstrip("board="))
        board.print_board()
        actions = self.on_turn(board)
        self.__send(actions)

    def __send(self, actions):
        msg = ",".join([x.value for x in actions])
        logger.info("Sending: {}".format(msg))
        self.socket.send(msg)

    def on_open(self, ws):
        logger.info("Connection established: {}".format(ws))

    def on_error(self, ws, error):
        logger.error(error)

    def on_close(self, ws):
        logger.info("### disconnected ###")
