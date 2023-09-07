package com.greentree.example.telegram.state;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public record PreStartState(ChatState next) implements ChatState {

    @Override
    public ChatState onMessage(AbsSender sender, Message message) {
        var text = message.getText();
        if ("/start".equals(text))
            return next;
        return this;
    }

    @Override
    public void init(AbsSender sender, long chatId) throws TelegramApiException {
        ChatState.send(sender, chatId, "Введите /start");
    }

}
