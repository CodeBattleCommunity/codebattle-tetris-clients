CC=g++
CFLAGS=-c
LDFLAGS=-pthread
SOURCES=CodeBattleCpp/CodeBattleCpp.cpp CodeBattleCppLibrary/BoardPoint.cpp CodeBattleCppLibrary/CommandBuilder.cpp CodeBattleCppLibrary/GameBoard.cpp CodeBattleCppLibrary/GameClientTetris.cpp CodeBattleCppLibrary/GlassBoard.cpp CodeBattleCppLibrary/LevelProgress.cpp CodeBattleCppLibrary/easywsclient/easywsclient.cpp
OBJECTS=$(SOURCES:.cpp=.o)
EXECUTABLE=tetris

all: $(SOURCES) $(EXECUTABLE)

$(EXECUTABLE): $(OBJECTS)
	$(CC) $(LDFLAGS) $(OBJECTS) -o $@

.cpp.o:
	$(CC) $(CFLAGS) $< -o $@
