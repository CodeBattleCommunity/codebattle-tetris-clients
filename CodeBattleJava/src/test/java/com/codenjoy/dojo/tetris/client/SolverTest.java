package com.codenjoy.dojo.tetris.client;

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

import com.codenjoy.dojo.client.AbstractTextBoard;
import com.codenjoy.dojo.client.Solver;
import com.codenjoy.dojo.services.Command;
import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.Point;
import org.junit.Before;
import org.junit.Test;

import static com.codenjoy.dojo.services.PointImpl.pt;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

public class SolverTest {

    private Dice dice;
    private Solver<AbstractTextBoard> ai;

    @Before
    public void setup() {
        dice = mock(Dice.class);
        when(dice.next(anyInt())).thenReturn(1);
        ai = new YourSolver(dice);
    }

    @Test
    public void should() {
        List<Command> expected = new ArrayList<>();
        expected.add(Command.LEFT);
        expected.add(Command.RIGHT);
        expected.add(Command.ROTATE_CLOCKWISE_180);
        asertAI("......." +
                "......I" +
                "..LL..I" +
                "...LI.I" +
                ".SSLI.I" +
                "SSOOIOO" +
                "..OOIOO",
                "T",
                pt(1, 2),
                new String[] { "I", "O", "L", "Z" },
                expected);
    }

    private void asertAI(String glass, String figureType,
            Point point, String[] futureFigures,
            List<Command> expected) {
        String actual = ai.get(BoardTest.getBoard(glass, figureType, point, futureFigures));
        assertEquals(String.join(", ", expected.stream().map(Object::toString).collect(toList())), actual);
    }

}
