#pragma once
#include "Command.h"
#include <list>
#include <string>

class CommandBuilder {
private:
	std::list<Command> commands;
	std::string parseCommand(Command command);
public:
	void addCommand(Command command);
	std::string buildString();
};