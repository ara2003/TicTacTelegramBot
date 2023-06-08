package com.greentree.example.telegram;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public record StartXOState(Game game, AiController controller) implements ChatState {

    public StartXOState() {
        this(new Game(), new SmartAiController(new Center(new NotLose(new RandomPosition()))));
    }

    @Override
    public ChatState onCallback(AbsSender sender, CallbackQuery query) throws TelegramApiException {
        {
            var pos = Integer.parseInt(query.getData());
            var x = pos / game().getWidth();
            var y = pos % game().getHeight();
            if (game.taken(x, y)) {
                ChatState.send(sender, query.getMessage().getChatId(), "This place already taken " + game.get(x, y));
                return this;
            }
            game.set(x, y, CellState.X);
            if (win(sender, query.getMessage().getChatId()))
                return null;
        }
        var move = controller.move(new GameAiInterface(game, CellState.O));
        game.set(move.first, move.seconde, CellState.O);
        if (win(sender, query.getMessage().getChatId()))
            return null;
        return this;
    }

    private boolean win(AbsSender sender, long chatId) throws TelegramApiException {
        var win = game.getWin();
        if (win != null) {
            var send = new SendMessage();
            if (win == CellState.Empty)
                send.setText("draw");
            else
                send.setText(win + " win");
            send.setChatId(chatId);
            send.setReplyMarkup(table());
            sender.execute(send);
            return true;
        }
        return false;
    }

    private InlineKeyboardMarkup table() {
        var buttons = new ArrayList<List<InlineKeyboardButton>>();
        for (var x = 0; x < game.getWidth(); x++) {
            var row = new ArrayList<InlineKeyboardButton>();
            buttons.add(row);
            for (var y = 0; y < game.getHeight(); y++) {
                var button = new InlineKeyboardButton();
                row.add(button);
                button.setText(switch (game.get(x, y)) {
                    case X -> "X";
                    case O -> "0";
                    case Empty -> "-";
                });
                button.setCallbackData((x * game().getHeight() + y) + "");
            }
        }
        var keyboard = new InlineKeyboardMarkup();
        keyboard.setKeyboard(buttons);
        return keyboard;
    }

    @Override
    public void init(AbsSender sender, long chatId) throws TelegramApiException {
        var send = new SendMessage();
        send.setText("Ваш ход:");
        send.setChatId(chatId);
        send.setReplyMarkup(table());
        sender.execute(send);
    }

}
