package com.greentree.example.telegram;

public record GameAiInterface(Game game, CellState my) implements AiInterface {

    @Override
    public int getLineToWin() {
        return game.getLineToWin();
    }

    @Override
    public int getWidth() {
        return game.getWidth();
    }

    @Override
    public int getHeight() {
        return game.getWidth();
    }

    @Override
    public AiCellState get(int x, int y) {
        var inGame = game.get(x, y);
        if (inGame == my)
            return AiCellState.My;
        if (inGame == CellState.Empty)
            return AiCellState.Empty;
        return AiCellState.Enemy;
    }

}
