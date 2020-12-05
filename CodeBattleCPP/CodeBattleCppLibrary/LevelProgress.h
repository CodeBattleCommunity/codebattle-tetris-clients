#pragma once
#pragma once
class LevelProgress {
private:
	int maxLevel;
	int currentLevel;
	int lastPassed;
public:
	LevelProgress(int maxLevel, int currentLevel, int lastPassed);
	int getMaxLevel();
	int getCurrentLevel();
	int getLastPassedLevel();
};