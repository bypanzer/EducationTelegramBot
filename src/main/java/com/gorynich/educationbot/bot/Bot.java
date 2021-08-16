package com.gorynich.educationbot.bot;

import com.gorynich.educationbot.bot.handler.Handler;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {
    public final static String BOT_NAME = "";
    public final static String BOT_TOKEN = "";
    Handler handler;

    private Long chatIDString;

    public Bot(){
        this.handler = new Handler();
    }


    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        //update.getUpdateId();
        chatIDString = update.getMessage().getChatId();
        handler.setUpdate(update);

            try {
                execute(handler.handle());
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }
}
