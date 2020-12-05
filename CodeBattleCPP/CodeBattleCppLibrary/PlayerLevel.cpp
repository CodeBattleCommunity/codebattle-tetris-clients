#include "PlayerLevel.h"


PlayerLevel::PlayerLevel()
{
	this->total = 0;
	this->current = 0;
	this->lastPassed = 0;
}

PlayerLevel::PlayerLevel(int total, int current, int lastPassed)
{
	this->total = total;
	this->current = current;
	this->lastPassed = lastPassed;
}
PlayerLevel::~PlayerLevel() {

}
int PlayerLevel::getTotal() {
	return this->total;
}
int PlayerLevel::getCurrent() {
	return this->current;
}
int PlayerLevel::getLastPassed() {
	return this->lastPassed;
}