/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2018 - 2020 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

const Api = (
  WSocket,
  Configuration,
  Direction,
  Element,
  Point,
  Board,
  Solver
) => {
  const {
    connectionString,
    isAdditionalLoggingEnabled,
    connectionTimeout,
  } = Configuration;
  let additionalLogging = false;

  const log = (string) => {
    if (isAdditionalLoggingEnabled) {
      console.log(string);
      if (additionalLogging) {
        printLogOnTextArea(string);
      }
    }
  };

  try {
    if (!!printBoardOnTextArea) {
      additionalLogging = true;
    }
  } catch (e) {
    log("No additional browser logging");
  }

  const connect = () => {
    let url = connectionString.replace("http", "ws");
    url = url.replace("board/player/", "ws?user=");
    url = url.replace("?code=", "&code=");

    const ws = new WSocket(url);

    log("Opening...");

    ws.on("open", () => {
      log("Web socket client opened " + url);
    });

    ws.on("close", () => {
      log("Web socket client closed");

      setTimeout(() => {
        log("Try to reconnect...");
        connect();
      }, connectionTimeout);
    });

    ws.on("message", (message) => {
      const pattern = new RegExp(/^board=(.*)$/);
      const parameters = message.match(pattern);
      const boardString = parameters[1];
      const answer = processBoard(boardString);
      ws.send(answer);
    });
  };

  const processBoard = (boardString) => {
    const solver = new Solver(Direction, Element);
    const parsedBoard = JSON.parse(boardString);
    const board = new Board(parsedBoard, Element, Point);
    const command = solver.get(board);
    const answer = command ? command.toString() : " ";
    const logMessage = `${board}\n\nAnswer: ${answer}\n-----------------------------------\n`;

    if (additionalLogging) {
      printBoardOnTextArea(board.toString());
    }

    log(logMessage);

    return answer;
  };

  return {
    start: connect,
  };
};
