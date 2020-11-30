#include "CommandBuilder.h"

void CommandBuilder::addCommand(Command command) {
	commands.push_back(command);
}

std::string CommandBuilder::parseCommand(Command command) {
	switch (command)
	{
	case Command::DOWN:
		return "DOWN";
	case Command::LEFT:
		return "LEFT";
	case Command::RIGHT:
		return "RIGHT";
	case Command::SUICIDE:
		return "SUICIDE";
	case Command::ROTATE_CLOCKWISE_90:
		return "ACT";
	case Command::ROTATE_CLOCKWISE_180:
		return "ACT(2)";
	case Command::ROTATE_CLOCKWISE_270:
		return "ACT(3)";
	default:
		return "";
	}
}

std::string CommandBuilder::buildString() {
	std::string result = "";
	for (auto command : commands) {
		result += parseCommand(command) + ",";
	}
	return result.substr(0, result.size() - 1);
}