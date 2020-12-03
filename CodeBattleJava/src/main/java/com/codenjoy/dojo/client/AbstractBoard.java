package com.codenjoy.dojo.client;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2018 Codenjoy
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


import com.codenjoy.dojo.services.printer.CharElements;
import com.codenjoy.dojo.services.Point;

import static com.codenjoy.dojo.services.PointImpl.pt;

import java.util.*;

public abstract class AbstractBoard<E extends CharElements> extends AbstractLayeredBoard<E> {

    public abstract E valueOf(char ch);

    public ClientBoard forString(String boardString) {
        return super.forString(boardString);
    }

    @Override
    public AbstractBoard forString(String... layers) {
        return (AbstractBoard) super.forString(layers);
    }

    /**
     * Список точек, соответствующих заданному типу
     * @param elements List of elements that we try to find.
     * @return All positions of element specified.
     */
    public List<Point> get(E... elements) {
        List<Point> result = new LinkedList<>();
        for (int layer = 0; layer < countLayers(); ++layer) {
            result.addAll(get(layer, elements));
        }
        return result;
    }

    /**
     * Проверяет, находится ли на данной позиции (X, Y) элемент соответствуюего типа.
     * @param x координата X.
     * @param y координата Y.
     * @param element тип элемента.
     * @return true если находится.
     */
    public boolean isAt(int x, int y, E element) {
        for (int layer = 0; layer < countLayers(); ++layer) {
            if (isAt(layer, x, y, element)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Проверяет, находится ли на данной позиции (X, Y) элемент соответствуюего типа.
     * @param point точка.
     * @param element тип элемента.
     * @return true если находится.
     */
    public boolean isAt(Point point, E element) {
        return isAt(point.getX(), point.getY(), element);
    }

    /**
     * Возвращает тип элемента на данной позиции (X, Y).
     * @param x координата X.
     * @param y координата Y.
     * @return тип элемента.
     */
    public E getAt(int x, int y) {
        List<E> at = getAllAt(x, y);
        if (at.isEmpty()) {
            return null;
        } else {
            return at.get(0);
        }
    }

    /**
     * Возвращает тип элемента на данной позиции (X, Y).
     * @param point точка.
     * @return тип элемента.
     */
    public E getAt(Point point) {
        return getAt(point.getX(), point.getY());
    }

    
    private List<E> getAllAt(int x, int y) {
        List<E> result = new LinkedList<>();
        if (pt(x, y).isOutOf(size)) {
            return result;
        }
        for (int layer = 0; layer < countLayers(); ++layer) {
            result.add(getAt(layer, x, y));
        }
        return result;
    }

    protected List<E> getAllAt(Point point) {
        return getAllAt(point.getX(), point.getY());
    }

    protected String boardAsString() {
        StringBuffer result = new StringBuffer();
        for (int layer = 0; layer < countLayers(); ++layer) {
            if (layer > 0) {
                result.append('\n');
            }
            result.append(boardAsString(layer));
        }
        return result.toString();
    }

    /**
     * Проверяет, находится ли на данной позиции (X, Y) элемент одного из типов.
     * @param x координата X.
     * @param y координата Y.
     * @param element типы элемента.
     * @return true если находится.
     */
    public boolean isAt(int x, int y, E... elements) {
        for (int layer = 0; layer < countLayers(); ++layer) {
            if (isAt(layer, x, y, elements)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Проверяет, находится ли на данной позиции (X, Y) элемент одного из типов.
     * @param point точка.
     * @param element типы элемента.
     * @return true если находится.
     */
    public boolean isAt(Point point, E... elements) {
        return isAt(point.getX(), point.getY(), elements);
    }

    /**
     * Проверяет, находится ли рядом с данной позицией (X, Y) элемент одного из типов.
     * @param x координата X.
     * @param y координата Y.
     * @param element типы элемента.
     * @return true если находится.
     */
    public boolean isNear(int x, int y, E element) {
        for (int layer = 0; layer < countLayers(); ++layer) {
            if (isNear(layer, x, y, element)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Проверяет, находится ли рядом с данной позицией (X, Y) элемент одного из типов.
     * @param point точка.
     * @param element типы элемента.
     * @return true если находится.
     */
    public boolean isNear(Point point, E element) {
        return isNear(point.getX(), point.getY(), element);
    }

    /**
     * Считает число элементов рядом с данной позицией (X, Y) элемент одного из типов.
     * @param x координата X.
     * @param y координата Y.
     * @param element типы элемента.
     * @return колличество.
     */
    public int countNear(int x, int y, E element) {
        int count = 0;

        for (int layer = 0; layer < countLayers(); ++layer) {
            count += countNear(layer, x, y, element);
        }

        return count;
    }

    /**
     * Считает число элементов рядом с данной позицией (X, Y) элемент одного из типов.
     * @param point точка.
     * @param element типы элемента.
     * @return колличество.
     */
    public int countNear(Point point, E element){
        return countNear(point.getX(), point.getY(), element);
    }

    /**
     * Возвращает список элементов рядом с данной позицией (X, Y) элемент одного из типов.
     * @param x координата X.
     * @param y координата Y.
     * @param element типы элемента.
     * @return список элементов.
     */
    public List<E> getNear(int x, int y) {
        List<E> result = new LinkedList<E>();

        for (int layer = 0; layer < countLayers(); ++layer) {
            result.addAll(getNear(layer, x, y));
        }

        return result;
    }
    /**
     * Возвращает список элементов рядом с данной позицией (X, Y) элемент одного из типов.
     * @param point точка.
     * @param element типы элемента.
     * @return список элементов.
     */
    public List<E> getNear(Point point) {
        return getNear(point.getX(), point.getY());
    }

    public void set(int x, int y, char ch) {
        set(0, x, y, ch);
    }

    public char[][] getField() {
        return field[0];
    }

}
