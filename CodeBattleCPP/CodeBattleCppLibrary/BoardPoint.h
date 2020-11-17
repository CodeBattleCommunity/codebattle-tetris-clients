#pragma once

class BoardPoint
{
public:
	BoardPoint();
	BoardPoint(int x, int y);
	~BoardPoint();
	int getX();
	int getY();
	bool isOutOfBoard(int size);
	void print();
private:
	int x;
	int y;
};