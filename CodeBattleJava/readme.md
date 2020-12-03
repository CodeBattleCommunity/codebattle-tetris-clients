## Инструкция

### Клиент на java

#### Подготовка 
Чтобы начать работать потребуется **Java 8** или выше. 
Так же Maven версии 3.6.3 или выше

0. Скопируйте клиент к себе

```bash

    git clone https://github.com/CodeBattleCommunity/codebattle-tetris-clients.git 

```

1. Импортируйте проект как "Maven project" в вашу IDE Intellij Idea (или Eclipse)

#### Подключение к серверу и начало игры

1. Чтобы подключится к серверу необходимо в файле CodeBattleJava\src\main\java\com\codenjoy\dojo\tetris\client\YourSolver.java прописать uri сервера игры

```java

    public static void main(String[] args) {
        WebSocketRunner.runClient(
                // скопируйте сюда адрес из браузера, на который перейдете после регистрации/логина
                "http://localhost:8080/codenjoy-contest/board/player/ziwpjz46y4z5567k7uup?code=3867579515136108220&gameName=tetris",
                new YourSolver(new RandomDice()),
                new Board());
    }

```

2. Метод - **getAnswer** в YourSolver.java должен возвращать в качестве ответа список действий (Command), которые вы хотите сделать.

```java
    private List<Command> getAnswerList(Board board) {
        System.out.println(board.getGlass().getAt(board.getCurrentFigurePoint()));
        List<Command> result = new ArrayList<Command>();
        result.add(Command.LEFT);
        result.add(Command.random(dice));
        result.add(Command.ROTATE_CLOCKWISE_180);

        return result;
    }

```


### Методы API для работы с сервером

Все необходимые методы привязаны к объекту классе Board, который передается как агрумент в главный метод **getAnswer**
Дополнительные методы придоставляет класс GlassBoard, объект которого можно получить методом board.getGlass() 


### Список доступных Actions (ход в игре)
Данный тип является обязательным для ответа в методе turn.


LEFT = "left" – передвижение фигурки влево/вправо;

RIGHT = "right" – передвижение фигурки вправо;

DOWN = "down" - приземление фигурки;

ROTATE_CLOCKWISE_90 = "act" - вращение фигурки по часовой стрелке на 90 градусов;

ROTATE_CLOCKWISE_180 = "act(2)" - вращение на 180 градусов;

ROTATE_CLOCKWISE_270 = "act(3)" - вращение против часовой стрелки на 90 градусов;

SUICIDE = "act(0,0)" - обнуление стакана (как и при его переполнении будут сняты штрафные очки).


