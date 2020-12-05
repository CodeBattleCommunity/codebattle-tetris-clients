#pragma once
class PlayerLevel
{
public:
	PlayerLevel();
	PlayerLevel(int total, int current, int lastPassed);
	~PlayerLevel();
	int getTotal();
	int getCurrent();
	int getLastPassed();
private:
	int total;
	int current;
	int lastPassed;
};

