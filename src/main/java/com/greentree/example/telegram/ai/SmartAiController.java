package com.greentree.example.telegram.ai;

import com.greentree.commons.util.cortege.Pair;
import com.greentree.example.telegram.Game;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public record SmartAiController() implements AiController {

    @Override
    public Pair<Integer, Integer> move(AiInterface api) {
        var info = new Info(api.toGame());
        System.out.println();
        System.out.println(info.game + " " + info.winRate());
        for (var c : info.children)
            System.out.println(c.game + " " + c.winRate());
        return info.bestMove();
    }

    private static class Info {

        public final Game game;
        public final List<ChildInfo> children = new ArrayList<>();
        public final CellState win;

        public Info(Game game) {
            this.game = game;
            this.win = game.getWin();
            if (win == null)
                for (int x = 0; x < game.getWidth(); x++)
                    for (int y = 0; y < game.getHeight(); y++)
                        if (!game.taken(x, y)) {
                            game.set(x, y, CellState.X);
                            children.add(new ChildInfo(x, y, game.inverse()));
                            game.set(x, y, CellState.Empty);
                        }
        }

        public Pair<Integer, Integer> bestMove() {
            return bestChild().move();
        }

        public ChildInfo bestChild() {
            children.sort(Comparator.comparing(x -> -x.winRate()));
            if (children.isEmpty())
                return null;
            return children.get(0);
        }

        public int winCount() {
            if (win == CellState.O)
                return 1;
            if (win != null)
                return 0;
            var sum = 0;
            for (var c : children)
                sum += c.loseCount();
            return sum;
        }

        public float loseRate() {
            return 1 - winRate();
        }

        public float winRate() {
            if (win == CellState.X)
                return 1;
            if (win == CellState.O)
                return 0;
            if (win == CellState.Empty)
                return 0.1f;
            var result = 0f;
            for (var c : children)
                result += c.winRate();
            return result / children.size();
        }

        public int loseCount() {
            if (win == CellState.X)
                return 1;
            if (win != null)
                return 0;
            var sum = 0;
            for (var c : children)
                sum += c.winCount();
            return sum;
        }

        public float drawRate() {
            return drawCount() * 1f / sumCount();
        }

        public int drawCount() {
            if (win == CellState.Empty)
                return 1;
            if (win != null)
                return 0;
            var sum = 0;
            for (var c : children)
                sum += c.drawCount();
            return sum;
        }

        private int sumCount() {
            return loseCount() + winCount();
        }

        private static class ChildInfo extends Info {

            private final int x;
            private final int y;

            public ChildInfo(int x, int y, Game game) {
                super(game);
                this.x = x;
                this.y = y;
            }

            public Pair<Integer, Integer> move() {
                return new Pair<>(x, y);
            }

        }

    }

}
