package com.greentree.example.telegram.ai;

import kotlin.Pair;

public class FirstEmpty implements AiController {

    @Override
    public Pair<Integer, Integer> move(AiInterface game) {
        for (var x = 0; x < game.getWidth(); x++) {
            for (var y = 0; y < game.getHeight(); y++) {
                if (!game.taken(x, y)) {
                    return new Pair<>(x, y);
                }
            }
        }
        return null;
    }

}
