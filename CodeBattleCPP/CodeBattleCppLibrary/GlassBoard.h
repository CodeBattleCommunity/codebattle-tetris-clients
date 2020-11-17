#pragma once

#include <list>
#include "Element.h"
#include "BoardPoint.h"

class GlassBoard {
public:
    GlassBoard();
    GlassBoard(Element** map, int map_size);
    ~GlassBoard();
    std::list<BoardPoint> getFreeSpace();
    std::list<BoardPoint> getFigures();
    std::list<BoardPoint> findAllElements(Element elem);
    bool isFree(int x, int y);
private:
    Element** map;
    int map_size;
};

/*

    @Override
    public Elements valueOf(char ch) {
        return Elements.valueOf(ch);
    }

    @Override
    protected int inversionY(int y) {
        return size() - 1 - y;
    }

    public boolean isFree(int x, int y) {
        return isAt(x, y, Elements.NONE);
    }

    public List<Point> getFigures() {
        return get(
                Elements.BLUE,
                Elements.CYAN,
                Elements.ORANGE,
                Elements.YELLOW,
                Elements.GREEN,
                Elements.PURPLE,
                Elements.RED
        );
    }

    public List<Point> getFreeSpace() {
        return get(Elements.NONE);
    }



*/