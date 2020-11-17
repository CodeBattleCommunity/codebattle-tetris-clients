#include "GlassBoard.h"


GlassBoard::GlassBoard() {
	this->map = nullptr;
	this->map_size = 0;
}

GlassBoard::GlassBoard(Element** map, int map_size) {
	this->map = map;
	this->map_size = map_size;
}

std::list<BoardPoint> GlassBoard::getFreeSpace() {
	return findAllElements(Element::NONE);
}
std::list<BoardPoint> GlassBoard::getFigures() {
	std::list<BoardPoint> points = findAllElements(Element::BLUE);
	points.splice(points.end(), findAllElements(Element::CYAN));
	points.splice(points.end(), findAllElements(Element::ORANGE));
	points.splice(points.end(), findAllElements(Element::YELLOW));
	points.splice(points.end(), findAllElements(Element::GREEN));
	points.splice(points.end(), findAllElements(Element::PURPLE));
	points.splice(points.end(), findAllElements(Element::RED));
	return points;
}
bool GlassBoard::isFree(int x, int y) {
	if (map[x][y] == Element::NONE) {
		return true;
	} else {
		return false;
	}
}

GlassBoard::~GlassBoard() {
	delete map;
}

std::list<BoardPoint> GlassBoard::findAllElements(Element elem) {
	std::list<BoardPoint> result;
	for (int j = 0; j < map_size; j++)
	{
		for (int i = 0; i < map_size; i++)
		{
			if (map[j][i] == elem) {
				result.push_back(BoardPoint(j, i));
			}
		}
	}
	return result;
}
