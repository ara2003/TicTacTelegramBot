package com.greentree.example.telegram;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
public class BotInitializer {

    private final LongPollingBot telegramBot;

    private BotSession session;

    public BotInitializer(LongPollingBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @PostConstruct
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            session = telegramBotsApi.registerBot(telegramBot);
        } catch (TelegramApiException e) {
        }
    }
    
    @PreDestroy
    public void terminate() throws TelegramApiException {
        session.stop();
    }

}