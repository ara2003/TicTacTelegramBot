package com.greentree.example.telegram;

public interface AiInterface {

    default boolean taken(int x, int y) {
        return get(x, y) != AiCellState.Empty;
    }

    AiCellState get(int x, int y);

    default Game toGame() {
        var game = new Game(getWidth(), getHeight(), getLineToWin());
        for (int x = 0; x < getWidth(); x++)
            for (int y = 0; y < getHeight(); y++) {
                var c = get(x, y);
                if (c == AiCellState.My)
                    game.set(x, y, CellState.X);
                if (c == AiCellState.Enemy)
                    game.set(x, y, CellState.O);
            }
        return game;
    }

    int getWidth();

    int getHeight();

    int getLineToWin();

}
