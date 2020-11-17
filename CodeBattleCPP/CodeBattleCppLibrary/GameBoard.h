#pragma once

#include "Element.h"
#include "GlassBoard.h"
#include "BoardPoint.h"
#include <list>

class GameBoard
{
public:
	GameBoard(GlassBoard* glassBoard, BoardPoint currentFigurePoint, Element currentFigureType, std::list<Element> futureFigures);
	GlassBoard* getGlassBoard();
    BoardPoint getCurrentFigurePoint();
    Element getCurrentFigureType();
    std::list<Element> getFutureFigures();
	~GameBoard();
private:
	GlassBoard* glassBoard;
	BoardPoint currentFigurePoint;
	Element currentFigureType;
	std::list<Element> futureFigures;
};