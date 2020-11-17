#pragma once

#include <string>
#include <thread>
#include "easywsclient\easywsclient.hpp"
#ifdef _WIN32
#pragma comment( lib, "ws2_32" )
#include <WinSock2.h>
#endif
#include <assert.h>
#include <stdio.h>
#include <iostream>
#include <string>
#include <memory>
#include <functional>
#include "rapidjson/document.h"
#include "rapidjson/writer.h"
#include "rapidjson/stringbuffer.h"
#include "Element.h"
#include "GameBoard.h"
#include "GlassBoard.h"

class GameClientTetris
{
	Element**map;
	GameBoard *board;
	GlassBoard* glassBoard;
	uint32_t map_size, player_x, player_y;


	easywsclient::WebSocket *web_socket;
	std::string path;

	bool is_running;
	std::thread *work_thread;
	void update_func(std::function<void()> _message_handler);

public:
	GameClientTetris(std::string _server);
	~GameClientTetris();

	void Run(std::function<void()> _message_handler);
	void LoderunnerAction(std::string action = "") {
		send(action);
	}
	GameBoard* get_GameBoard() { return board; }
private:
	void send(std::string msg)
	{
		std::cout << "Sending: " << msg << std::endl;
		web_socket->send(msg);
	}
	
	Element getElement(std::string val) {
		Element elem = Element::NONE;
		/*
		BLUE = 'I',
	CYAN = 'J',
	ORANGE = 'L',
	YELLOW = 'O',
	GREEN = 'S',
	PURPLE = 'T',
	RED = 'Z',
	NONE = '.',
		*/
		if (val == "I") {
			elem = Element::BLUE;
		} else if (val == "J") {
			elem = Element::CYAN;
		} else if (val == "L") {
			elem = Element::ORANGE;
		} else if (val == "O") {
			elem = Element::YELLOW;
		} else if (val == "S") {
			elem = Element::GREEN;
		} else if (val == "T") {
			elem = Element::PURPLE;
		} else if (val == "Z") {
			elem = Element::RED;
		}
		return elem;
	}
};
