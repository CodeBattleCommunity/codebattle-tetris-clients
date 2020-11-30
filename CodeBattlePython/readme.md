## Инструкция
За основу клиента был взят https://github.com/Permyak-Logy/TetrisBattle .

### Клиент на python

#### Подготовка virtualenv и установка зависимостей
Чтобы начать работать потребуется **Python 3.6** или выше. 

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
list из действий, которые вы хотите сделать - ENUM вариантов CodeBattlePython/tetris_client/internals/tetris_action.py. Чтобы отправить только одно действие - лист с 1 элементом.

```python
    # 1-в-1 пример также представлен в самом файле CodeBattlePython/tetris_client/__main__.py

def turn(gcb: Board) -> TetrisAction:
    # this function must return list actions from TetrisAction: tetris_client/internals/tetris_action.py
    #     LEFT = 'left'
    #     RIGHT = 'right'
    #     DOWN = 'down'
    #     ACT = 'act'
    #     ACT_2 = 'act(2)'
    #     ACT_3 = 'act(3),'
    #     ACT_0_0 = 'act(0,0)'
    # change return below to your code (right now its returns random aciton):
    # код ниже является примером и сэмплом для демонстрации - после подстановки корректного URI к своей игре
    # запустите клиент и посмотрите как отображаются изменения в UI игры и что приходит как ответ от API 
    elem = gcb.get_current_figure_type()
    print(gcb.get_future_figures())
    print(gcb.get_current_figure_point()) 
    print(gcb.get_current_figure_type())
    print(gcb.find_element(elem))
    actions = [x for x in TetrisAction if x != 'act(0,0)']
    # return [TetrisAction.LEFT] - example how to send only one action, list with 1 element
    return [TetrisAction.LEFT, random.choice(actions), random.choice(actions)]  # это те действия, которые выполнятся на игровом сервере в качестве вашего хода

```


Метод **turn** должен возвращать действие, которое вы планируете передать на Сервер в качестве хода. 


3. Чтобы запустить игру

```bash

    python tetris_client

```

### Описание ответа сервера 

Пример ответа от Сервера игры, который приходит по Websocket соединению c обновлением статуса игры

Строка в layers[0] равна площади поля (18 на 18 символов).

'currentFigurePoint':{'x':4,'y':9}, - это координата "якоря" фигуры точки вращения
подробнее про точки вращения здесь - http://codebattle2020.westeurope.cloudapp.azure.com/codenjoy-contest/resources/help/tetris.html (внимательно смотрите иллюстрацию с фигурами)

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

Все необходимые методы привязаны к объекту классе Board, который передается как агрумент в главный метод turn - https://github.com/CodeBattleCommunity/codebattle-tetris-clients/blob/python-client/CodeBattlePython/tetris_client/__main__.py#L12.


### Список методов API для работы с игровым "стаканом" (доской), класс Board: 

[tetris_client/internals/board.py](tetris_client/internals/board.py)

**get_current_figure_point()** - возвращает координаты текущей фигуры (объект класса Point), а именно координаты "якорной" точки фигуры вокруг которой совершается вращение

**get_current_figure_type()** - возвращает тип текущей фигуры

**get_future_figures()** - возвращает лист следующих фигур

**get_element_at(point: Union[Point, Tuple(int)])** - возвращает фигуру (объект класса Element), находящуюся на доске в переданных координатах

**has_element_at(point: Union[Point, Tuple(int)])** - проверяет есть ли в указанных координатах фигура

**is_element_at(point: Union[Point, Tuple(int)], element_object: Union[Element, str])** - проверяет соответствует ли фигура в 'element_object' фигуре на доске в указанных координатах

**predict_figure_points_after_rotation(x: int = None, y: int = None, figure: Union[Element, Text] = None, rotation: int = 0)** - если не передавать x, y или figure, то метод работает с текущей фигурой. Rotate - количество вращений, которые необходимо сделать с фигурой - 0 фигура не вращается, метод в ответ возвращает все координаты фигуры (всех её частей, а не только "якорной" точки вокруг которой совершается вращение), rotation = 1 - поворот на 90 градусов, rotation = 2 - координаты при повороте фигуры на 180 градусов и т.д. 


### Список методов API для работы с координатами, класс Point: 


[tetris_client/internals/point.py](tetris_client/internals/point.py)

**Point(x, y).is_out_of_board(board_size: int = 18)**  - проверяет находится ли координата в рамках игровой доски, дефолтное значение размера доски - 18 

**Point(x, y).shift_left(self, delta: int = 1)** - сдвигает координату на delta шагов влево (по умолчанию 1)

**Point(x, y).shift_right(self, delta: int = 1)** - сдвигает координату на delta шагов вправо (по умолчанию 1)

**Point(x, y).shift_top(self, delta: int = 1)** - сдвигает координату на delta шагов вверх (по умолчанию 1)

**Point(x, y).shift_bottom(self, delta: int = 1)** - сдвигает координату на delta шагов вниз (по умолчанию 1)



### Список доступных Actions (ход в игре)
Данный тип является обязательным для ответа в методе turn.

Список доступных actions - [tetris_client/internals/tetris_action.py](tetris_client/internals/tetris_action.py) 

LEFT = "left" – передвижение фигурки влево/вправо;

RIGHT = "right" – передвижение фигурки вправо;

DOWN = "down" - приземление фигурки;

ACT = "act" - вращение фигурки по часовой стрелке на 90 градусов;

ACT_2 = "act(2)" - вращение на 180 градусов;

ACT_3 = "act(3)" - вращение против часовой стрелки на 90 градусов;

ACT_0_0 = "act(0,0)" - обнуление стакана (как и при его переполнении будут сняты штрафные очки).


