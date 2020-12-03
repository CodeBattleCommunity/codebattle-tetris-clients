# Клиент на С++
 Для запуска нужно установить Visual Studio Community 2019.
 
 Пишем код для бота в этом файле
 
codebattle-tetris-clients\CodeBattleCPP\CodeBattleCpp\CodeBattleCpp.cpp


```
gcb->Run([&]()
	{
			//ПИСАТЬ КОД ЗДЕСЬ!!!!!
	});
```

Для формирования команд используй класс CommandBuilder

```
gcb->Run([&]()
	{
	    CommandBuilder builder;
            //добавляем комманды
	    builder.addCommand(Command::LEFT);
	    builder.addCommand(Command::LEFT);
	    builder.addCommand(Command::DOWN);
            //генерируем строку для отправки
	    std::string action = builder.buildString();
            //отправляем команду на сервер
	    gcb->sendAction(action);
	});
```

Для получения GameBoard и GlassBoard пишем таким образом


```
gcb->Run([&]()
	{
          GameBoard* gb = gcb->get_GameBoard();
          GlassBoard* glassBoard = gb->getGlassBoard();
	});
```
