#include "BoardPoint.h"
#include <iostream>

BoardPoint::BoardPoint() {

}

BoardPoint::BoardPoint(int x, int y)
{
	this->x = x;
	this->y = y;
}
BoardPoint::~BoardPoint() {

}
int BoardPoint::getX() {
	return this->x;
}
int BoardPoint::getY() {
	return this->y;
}
bool BoardPoint::isOutOfBoard(int size) {
	return x >= size || y >= size || x < 0 || y < 0;
}

void BoardPoint::print() {
	std::cout << "[" << x << "," << y << "]" << "\n";
}
