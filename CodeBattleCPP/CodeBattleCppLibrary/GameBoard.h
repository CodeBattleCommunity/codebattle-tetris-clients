#pragma once

#include "Element.h"
#include "GlassBoard.h"
#include "BoardPoint.h"
#include "PlayerLevel.h"
#include <list>

class GameBoard
{
public:
	GameBoard(GlassBoard* glassBoard, BoardPoint currentFigurePoint, Element currentFigureType, std::list<Element> futureFigures, PlayerLevel &playerLevel);
	GlassBoard* getGlassBoard();
    BoardPoint getCurrentFigurePoint();
    Element getCurrentFigureType();
    std::list<Element> getFutureFigures();
	PlayerLevel getPlayerLevel();
	~GameBoard();
private:
	GlassBoard* glassBoard;
	BoardPoint currentFigurePoint;
	Element currentFigureType;
	std::list<Element> futureFigures;
	PlayerLevel playerLevel;
};