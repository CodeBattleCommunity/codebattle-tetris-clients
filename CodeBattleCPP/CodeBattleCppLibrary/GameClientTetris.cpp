#include "GameClientTetris.h"
#include "rapidjson/document.h"
#include "rapidjson/writer.h"
#include "rapidjson/stringbuffer.h"
#include <iostream>
#include <string>
#include <cstring>
#include <math.h>

GameClientTetris::GameClientTetris(std::string _server)
{
	map = nullptr;
	board = nullptr;
	glassBoard = nullptr;
	levelProgress = nullptr;

	path = _server.replace(_server.find("http"), sizeof("http")-1, "ws");
	path = path.replace(path.find("board/player/"),sizeof("board/player/")-1,"ws?user=");
	path = path.replace(path.find("?code="),sizeof("?code=")-1,"&code=");

	is_running = false;
}

GameClientTetris::~GameClientTetris()
{
	is_running = false;
	work_thread->join();
}

void GameClientTetris::Run(std::function<void()> _message_handler)
{
	is_running = true;
	work_thread = new std::thread(&GameClientTetris::update_func, this, _message_handler);
}

void GameClientTetris::update_func(std::function<void()> _message_handler)
{
#ifdef _WIN32
	WSADATA wsaData;

	if (WSAStartup(MAKEWORD(2, 2), &wsaData))
		throw new std::exception("WSAStartup Failed.\n");
	else
		std::cout << "Connection established" << std::endl;
#endif

	web_socket = easywsclient::WebSocket::from_url(path);
	if (web_socket == nullptr)is_running = false;
	while (is_running)
	{
		web_socket->poll();
		web_socket->dispatch([&](const std::string &message)
		{
			Element currentFigureType;
			std::list<Element> futureFigures;


			std::size_t pos = message.find("=");
			std::string json = message.substr(pos + 1);
			rapidjson::Document d;
			d.Parse(json.c_str());
			//currentFigurePoint
			int x = 0;
			int y = 0;
			for (auto& item : d["currentFigurePoint"].GetObject()) {
				if (item.name == "x") {
					x = item.value.GetInt();
				}
				else {
					y = item.value.GetInt();
				}
			}
			BoardPoint currentFigurePoint(x, y);
			//currentFigureType
			currentFigureType = getElement(d["currentFigureType"].GetString());
			//futureFigures
			for (auto& item : d["futureFigures"].GetArray()) {
				futureFigures.push_back(getElement(item.GetString()));
			}
		
			//layers
			rapidjson::Value& layers = d["layers"];
			std::string mapStr = layers[0].GetString();
			int length = mapStr.length();

			std::list<char> listChars;
			for (auto item : mapStr) {
				listChars.push_back(item);
			}
			
			int maxLevel = d["levelProgress"]["total"].GetInt();
			int currentLevel = d["levelProgress"]["current"].GetInt();
			int lastPassed = d["levelProgress"]["lastPassed"].GetInt();
			levelProgress = new LevelProgress(maxLevel, currentLevel, lastPassed);

			int line = (int)sqrt(length);

			map = new Element*[line];
			for (uint32_t j = 0; j < line; j++)
			{
				map[j] = new Element[line];
				for (uint32_t i = 0; i < line; i++)
				{
					map[j][i] = Element::NONE;
				}
			}

			for (uint32_t j = 0; j < line; j++)
			{
				for (uint32_t i = 0; i < line; i++)
				{
					std::string point = std::string(1, listChars.front());
					map[j][i] = getElement(point);
					listChars.pop_front();
				}
			}
			std::cout << "\n";
			for (uint32_t j = 0; j < line; j++)
			{
				for (uint32_t i = 0; i < line; i++)
				{
					std::cout << map[j][i] << " ";
				}
				std::cout << "\n";
			}
			glassBoard = new GlassBoard(map, line);
			board = new GameBoard(glassBoard, currentFigurePoint, currentFigureType, futureFigures, levelProgress);
			_message_handler();
		});
	}
	if (web_socket)web_socket->close();

#ifdef _WIN32
	WSACleanup();
#endif
}
