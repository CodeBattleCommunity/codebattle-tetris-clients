#include <iostream>
#include <random>

#include "GameClientTetris.h"

void main()
{
	srand(time(0));

	GameClientTetris *gcb = new GameClientTetris("http://localhost:8080/codenjoy-contest/board/player/9sahhltizo730tp22kr1?code=7816623675811590848&gameName=tetris");
	gcb->Run([&]()
	{
			//ÏÈÑÀÒÜ ÊÎÄ ÇÄÅÑÜ!!!!!
			GameBoard* gb = gcb->get_GameBoard();
			GlassBoard* glassBoard = gb->getGlassBoard();
			gcb->LoderunnerAction("LEFT");
	});

	getchar();
}
