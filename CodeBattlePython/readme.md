## ENG-language Instruction
## Python Client

## Server Response Example

## API Methods to communicate with Game Server


## RU-language Инструкция

### Клиент на python


#### Подготовка virtualenv и установка зависимостей
Чтобы начать работать потребуется Python 3.6 или выше. 

0. Скопируйте клиент к себе

```bash

    git clone https://github.com/CodeBattleCommunity/codebattle-tetris-clients.git 

```

1. Создание virtual environment для работы:

```bash

    python3 -m venv env

```

2. Активация virtual env 
```bash

    source env/bin/activate

```

3. Перейдите в директорию codebattle-tetris-clients/CodeBattlePython и установите необходимые зависимости

```bash

    cd codebattle-tetris-clients/CodeBattlePython
    pip install -r requirements.txt


```


4. Добавьте tetris_client в PYTHONPATH

```bash

    export PYTHONPATH=$PYTHONPATH:./
    
```

#### Подключение к серверу и начало игры

1. Чтобы подключится к серверу необходимо в файле CodeBattlePython/src/main.py прописать uri сервера игры в CodeBattlePython/tetris_client/__main__.py

```python

    if __name__ == '__main__':
        # в uri переменную необходимо поместить url с игрой для своего пользователя
        # put your game url in the 'uri' path 1-to-1 as you get in UI 
        uri = "http://codebattle2020.westeurope.cloudapp.azure.com/codenjoy-contest/board/player/9r84saxen4c3whdvqfhx?code=3106303325539433635&gameName=tetris"
        main(uri)

```

2. Метод - **turn** в CodeBattlePython/tetris_client/__main__.py должен возвращать в качестве ответа
один из ENUM вариантов CodeBattlePython/tetris_client/internals/tetris_action.py. 

Метод **turn** должен возвращать действие, которое вы планируете передать на Сервер в качестве хода. 


3. Чтобы запустить игру

```bash

    python tetris_client

```

### Описание ответа сервера 

Пример ответа от Сервера игры, который приходит по Websocket соединению c обновлением статуса игры

Строка в layers[0] равна площади поля (18 на 18 символов).


```python

    {
    'currentFigurePoint':{'x':4,'y':9},
    'currentFigureType':'O',
    'futureFigures':['S', 'Z', 'I', 'O'],
    'layers':[
        '..................
        ........OO........
        ........OO........
        ..................
        ..................
        ..................
        ..................
        ..................
        ..................
        ..................
        ..................
        ..................
        ..................
        ..................
        ..I...............
        ..I......OO.......
        ..IOO..SSOOZZ.....
        ..IOO.SSIIIIZZ....'
    ]
    }

```

### Методы API для работы с сервером

