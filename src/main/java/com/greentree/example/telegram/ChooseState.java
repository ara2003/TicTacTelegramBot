package com.greentree.example.telegram;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record ChooseState(String text, Map<String, ChatState> nextStates) implements ChatState {

    @Override
    public ChatState onCallback(AbsSender sender, CallbackQuery query) throws TelegramApiException {
        var text = query.getData();
        if (nextStates.containsKey(text))
            return nextStates.get(text);
        return this;
    }

    @Override
    public void init(AbsSender sender, long chatId) throws TelegramApiException {
        var keyboard = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> first_row = new ArrayList<InlineKeyboardButton>();
        var buttons = List.of(first_row);
        for (var name : nextStates.keySet()) {
            var button = new InlineKeyboardButton();
            button.setText(name);
            button.setCallbackData(name);
            first_row.add(button);
        }
        keyboard.setKeyboard(buttons);
        var send = new SendMessage();
        send.setText(text);
        send.setChatId(chatId);
        send.setReplyMarkup(keyboard);
        sender.execute(send);
    }

}
