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
import com.codenjoy.dojo.services.FigureRotator;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.Rotation;
import com.codenjoy.dojo.tetris.model.Elements;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import static com.codenjoy.dojo.services.PointImpl.pt;

/**
 * Класс, обрабатывающий строковое представление доски в виде JSON. Содержит ряд
 * унаследованных методов {@see AbstractTextBoard}, но ты можешь добавить сюда
 * любые свои методы на их основе.
 */
public class Board extends AbstractTextBoard {

    /**
     * Возвращает позицию якорной точки текущей фигуры
     * @return
     */
    public Point getCurrentFigurePoint() {
        JSONObject point = getJson().getJSONObject("currentFigurePoint");
        int x = point.getInt("x");
        int y = point.getInt("y");
        return pt(x, y);
    }

    /**
     * Возвращает тип текущей фигуры
     * @return
     */
    public Elements getCurrentFigureType() {
        if (!getJson().has("currentFigureType")) {
            return null;
        }
        String figureType = getJson().getString("currentFigureType");
        return getElement(figureType);
    }

    /**
     * Возвращает список следующих фигур
     * @return
     */
    public List<Elements> getFutureFigures() {
        List<Elements> result = new LinkedList<>();
        for (Object figure : getJson().getJSONArray("futureFigures")) {
            result.add(getElement((String) figure));
        }
        return result;
    }

    private JSONObject getJson() {
        return new JSONObject(data);
    }

    /**
     * Возвращает объект стакана, позволяющий вычислять дополнительную информацию о игровом поле
     * @return
     */
    public GlassBoard getGlass() {
        String glassString = getJson().getJSONArray("layers").getString(0);
        return (GlassBoard) new GlassBoard().forString(glassString);
    }

    private Elements getElement(String figureType) {
        char ch = figureType.charAt(0);
        return Elements.valueOf(ch);
    }

    /**
     * Позволяет определить координаты точек текущей фигуры после применения
     * операции вращения к ней. Поворот производится от исходного положения фигуры
     * (в котором она появляется в стакане)!
     * 
     * Экспериментальный метод. Использовать осторожно!
     * 
     * @param rotation Поворот фигуры по часовой стрелке.
     * @return Набор точек текущей фигуры с координатами после вращения данной
     *         фигуры.
     */
    public Point[] predictCurrentFigurePoints(Rotation rotation) {
        return predictCurrentFigurePoints(getCurrentFigurePoint(), rotation);
    }

    /**
     * Позволяет определить координаты точек текущей фигуры в исходном положении. (в
     * котором она появляется в стакане)!
     * 
     * Экспериментальный метод. Использовать осторожно!
     * 
     * @return Набор точек текущей фигуры с координатами после вращения данной
     *         фигуры.
     */
    public Point[] predictCurrentFigurePoints() {
        return predictCurrentFigurePoints(Rotation.CLOCKWIZE_0);
    }

    /**
     * Позволяет определить координаты точек текущей фигуры после применения
     * операции вращения к ней. Поворот производится от исходного положения фигуры
     * (в котором она появляется в стакане)!
     * 
     * Экспериментальный метод. Использовать осторожно!
     * 
     * @param rotation Поворот фигуры по часовой стрелке.
     * @return Набор точек текущей фигуры с координатами после вращения данной
     *         фигуры.
     */
    public Point[] predictCurrentFigurePoints(Point figureAnchor, Rotation rotation) {
        return FigureRotator.predictCurrentFigurePoints(rotation, figureAnchor, getCurrentFigureType());
    }

    /**
     * Позволяет определить координаты точек текущей фигуры в исходном положении. (в
     * котором она появляется в стакане)!
     * 
     * Экспериментальный метод. Использовать осторожно!
     * 
     * @param figureAnchor Точка - "якорь" фигуры.
     * @return Набор точек текущей фигуры с координатами после вращения данной
     *         фигуры.
     */
    public Point[] predictCurrentFigurePoints(Point figureAnchor) {
        return predictCurrentFigurePoints(figureAnchor, Rotation.CLOCKWIZE_0);
    }

}