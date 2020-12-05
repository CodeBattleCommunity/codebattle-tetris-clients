#pragma once

#include "Element.h"
#include "GlassBoard.h"
#include "BoardPoint.h"
#include "LevelProgress.h"
#include <list>

class GameBoard
{
public:
	GameBoard(GlassBoard* glassBoard, BoardPoint currentFigurePoint, Element currentFigureType, std::list<Element> futureFigures, LevelProgress* levelprogress);
	GlassBoard* getGlassBoard();
	LevelProgress* getLevelProgress();
    BoardPoint getCurrentFigurePoint();
    Element getCurrentFigureType();
    std::list<Element> getFutureFigures();
	~GameBoard();
private:
	GlassBoard* glassBoard;
	LevelProgress* levelProgress;
	BoardPoint currentFigurePoint;
	Element currentFigureType;
	std::list<Element> futureFigures;
};