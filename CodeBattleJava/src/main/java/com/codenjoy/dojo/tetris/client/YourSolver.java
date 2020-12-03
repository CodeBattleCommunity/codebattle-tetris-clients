package com.codenjoy.dojo.tetris.client;

import static com.codenjoy.dojo.services.Command.DOWN;
import static com.codenjoy.dojo.services.Command.LEFT;
import static com.codenjoy.dojo.services.Command.RIGHT;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2016 Codenjoy
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

import com.codenjoy.dojo.client.AbstractJsonSolver;
import com.codenjoy.dojo.client.WebSocketRunner;
import com.codenjoy.dojo.services.Command;
import com.codenjoy.dojo.services.CommandChain;
import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.RandomDice;

/**
 * User: your name
 * Это твой алгоритм AI для игры. Реализуй его на свое усмотрение.
 * Обрати внимание на {@see YourSolverTest} - там приготовлен тестовый
 * фреймворк для тебя.
 */
public class YourSolver extends AbstractJsonSolver<Board> {

    private Dice dice;

    public YourSolver(Dice dice) {
        this.dice = dice;
    }

    @Override
    public String getAnswer(Board board) {
        return getAnswerList(board).toString();
    }

    private CommandChain getAnswerList(Board board) {
        System.out.println(board.getGlass().getAt(board.getCurrentFigurePoint()));
        return Command.LEFT
                .then(Command.random(dice))
                .then(Command.ROTATE_CLOCKWISE_180);

    }

    public static void main(String[] args) {
        WebSocketRunner.runClient(
                // скопируйте сюда адрес из браузера, на который перейдете после регистрации/логина
                "http://localhost:8080/codenjoy-contest/board/player/ziwpjz46y4z5567k7uup?code=3867579515136108220&gameName=tetris",
                new YourSolver(new RandomDice()),
                new Board());
    }

}
