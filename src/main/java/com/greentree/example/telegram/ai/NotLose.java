package com.greentree.example.telegram.ai;

import kotlin.Pair;

public record NotLose(AiController controller) implements AiController {

    @Override
    public Pair<Integer, Integer> move(AiInterface api) {
        var game = api.toGame();
        for (int x = 0; x < api.getWidth(); x++)
            for (int y = 0; y < api.getHeight(); y++)
                if (!game.taken(x, y)) {
                    game.set(x, y, CellState.X);
                    var win = game.getWin();
                    if (win == CellState.X)
                        return new Pair<>(x, y);
                    game.set(x, y, CellState.Empty);
                }
        for (int x = 0; x < api.getWidth(); x++)
            for (int y = 0; y < api.getHeight(); y++)
                if (!game.taken(x, y)) {
                    game.set(x, y, CellState.O);
                    var win = game.getWin();
                    if (win == CellState.O)
                        return new Pair<>(x, y);
                    game.set(x, y, CellState.Empty);
                }
        return controller.move(api);
    }

}
