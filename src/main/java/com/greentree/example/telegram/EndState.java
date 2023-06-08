package com.greentree.example.telegram;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class EndState implements ChatState {

    @Override
    public ChatState onMessage(AbsSender sender, Message message) throws TelegramApiException {
        return null;
    }

    @Override
    public ChatState onCallback(AbsSender sender, CallbackQuery query) throws TelegramApiException {
        return null;
    }

    @Override
    public void init(AbsSender sender, long chatId) throws TelegramApiException {
    }

}
