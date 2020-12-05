#include "Element.h"
#include "BoardPoint.h"
#include "GameBoard.h"

GameBoard::GameBoard(GlassBoard* glassBoard, BoardPoint currentFigurePoint, Element currentFigureType, std::list<Element> futureFigures, PlayerLevel &playerLevel) {
	this->glassBoard = glassBoard;
	this->currentFigurePoint = currentFigurePoint; 
	this->currentFigureType = currentFigureType;
	this->futureFigures = futureFigures;
	this->playerLevel = playerLevel;
}

BoardPoint GameBoard::getCurrentFigurePoint() {
	return currentFigurePoint;
}
Element GameBoard::getCurrentFigureType() {
	return currentFigureType;
}
std::list<Element> GameBoard::getFutureFigures() {
	return futureFigures;
}
PlayerLevel GameBoard::getPlayerLevel() {
	return playerLevel;
}
GlassBoard* GameBoard::getGlassBoard() {
	return glassBoard;
}
GameBoard::~GameBoard() {
	delete glassBoard;
}

