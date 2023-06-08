package com.greentree.example.telegram;

import com.greentree.commons.util.cortege.Pair;

public class RandomPosition implements AiController {

    @Override
    public Pair<Integer, Integer> move(AiInterface game) {
        while (true) {
            var x = (int) (Math.random() * game.getWidth());
            var y = (int) (Math.random() * game.getHeight());
            if (game.taken(x, y))
                continue;
            return new Pair<>(x, y);
        }
    }

}
