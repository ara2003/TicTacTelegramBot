package com.greentree.example.telegram;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface ChatState {

    default ChatState onMessage(AbsSender sender, Message message) throws TelegramApiException {
        send(sender, message.getChatId(), "error onMessage");
        return null;
    }

    static void send(AbsSender sender, long chatId, String text) throws TelegramApiException {
        var m = new SendMessage();
        m.setChatId(chatId);
        m.setText(text);
        sender.execute(m);
    }

    default ChatState onCallback(AbsSender sender, CallbackQuery query) throws TelegramApiException {
        send(sender, query.getMessage().getChatId(), "error onCallback");
        return null;
    }

    void init(AbsSender sender, long chatId) throws TelegramApiException;

}
