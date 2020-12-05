#include "LevelProgress.h"

LevelProgress::LevelProgress(int maxLevel, int currentLevel, int lastPassed) {
	this->maxLevel = maxLevel;
	this->currentLevel = currentLevel;
	this->lastPassed = lastPassed;
}
int LevelProgress::getMaxLevel() {
	return this->maxLevel;
}
int LevelProgress::getCurrentLevel() {
	return this->currentLevel;
}
int LevelProgress::getLastPassedLevel() {
	return this->lastPassed;
}
