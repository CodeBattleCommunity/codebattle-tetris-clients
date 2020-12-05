#include <iostream>
#include <random>

#include "../CodeBattleCppLibrary/GameClientTetris.h"
#include "../CodeBattleCppLibrary/CommandBuilder.h"

int main()
{
	srand(time(0));

	GameClientTetris *gcb = new GameClientTetris("http://localhost:8080/codenjoy-contest/board/player/9sahhltizo730tp22kr1?code=7816623675811590848&gameName=tetris");
	gcb->Run([&]()
	{
			//write down code here
			GameBoard* gb = gcb->get_GameBoard();
			GlassBoard* glassBoard = gb->getGlassBoard();
			LevelProgress* lp = gb->getLevelProgress();
			std::cout << "currentLevel: " << lp->getCurrentLevel() << "\n";
			std::cout << "lastPassedLevel: " << lp->getLastPassedLevel() << "\n";
			std::cout << "maxLevel: " << lp->getMaxLevel() << "\n";
			std::cout << "currentFigurePoints START" << "\n";
			CommandBuilder builder;
			builder.addCommand(Command::LEFT);
			builder.addCommand(Command::LEFT);
			builder.addCommand(Command::DOWN);
			std::string action = builder.buildString();

			gcb->sendAction(action);
	});

	getchar();
}
