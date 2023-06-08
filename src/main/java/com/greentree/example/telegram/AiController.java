package com.greentree.example.telegram;

import com.greentree.commons.util.cortege.Pair;

public interface AiController {

    Pair<Integer, Integer> move(AiInterface api);

}
