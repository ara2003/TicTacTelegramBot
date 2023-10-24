package com.greentree.example.telegram.ai;

import com.greentree.example.telegram.Game;
import kotlin.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public record SmartAiController() implements AiController {

    @Override
    public Pair<Integer, Integer> move(AiInterface api) {
        var info = new Info(api.toGame(), CellState.O);
        return info.bestMove();
    }

    private static class Info {

        public final Game game;
        public final List<ChildInfo> children = new ArrayList<>();
        public final CellState win;
        private final CellState winState;

        public Info(Game game, CellState winState) {
            this.winState = winState;
            this.game = game;
            this.win = game.getWin();
            if (win == null)
                for (int x = 0; x < game.getWidth(); x++)
                    for (int y = 0; y < game.getHeight(); y++)
                        if (!game.taken(x, y)) {
                            game.set(x, y, winState.toInverse());
                            children.add(new ChildInfo(x, y, game, winState.toInverse()));
                            game.set(x, y, CellState.Empty);
                        }
        }

        public Pair<Integer, Integer> bestMove() {
            return bestChild().move();
        }

        public ChildInfo bestChild() {
            return children.stream().min((Comparator.comparing(x -> -x.winRate()))).get();
        }

        public float winRate() {
            if (win == winState)
                return 1f;
            if (win != null)
                return 0f;
            var sum = 1f;
            for (var c : children)
                sum *= (1 - c.winRate());
            return sum * .9f;
        }

        private static class ChildInfo extends Info {

            private final int x;
            private final int y;

            public ChildInfo(int x, int y, Game game, CellState winState) {
                super(game, winState);
                this.x = x;
                this.y = y;
            }

            public Pair<Integer, Integer> move() {
                return new Pair<>(x, y);
            }

        }

    }

}
