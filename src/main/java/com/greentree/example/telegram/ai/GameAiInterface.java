package com.greentree.example.telegram.ai;

import com.greentree.example.telegram.Game;

public record GameAiInterface(Game game, CellState my) implements AiInterface {

    @Override
    public AiCellState get(int x, int y) {
        var inGame = game.get(x, y);
        if (inGame == my)
            return AiCellState.My;
        if (inGame == CellState.Empty)
            return AiCellState.Empty;
        return AiCellState.Enemy;
    }

    @Override
    public int getWidth() {
        return game.getWidth();
    }

    @Override
    public int getHeight() {
        return game.getHeight();
    }

    @Override
    public int getLineToWin() {
        return game.getLineToWin();
    }

}
