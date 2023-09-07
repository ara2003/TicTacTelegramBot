package com.greentree.example.telegram.ai;

import com.greentree.commons.util.cortege.Pair;

public record Center(AiController controller) implements AiController {

    @Override
    public Pair<Integer, Integer> move(AiInterface api) {
        var x = api.getWidth() / 2;
        var y = api.getHeight() / 2;
        if (!api.taken(x, y))
            return new Pair<>(x, y);
        return controller.move(api);
    }

}
