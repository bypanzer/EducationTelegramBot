package com.gorynich.educationbot.bot.reply;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

/*
Абстрактный класс Reply является родительским для
всех классов, обеспечивающих отправку сообщений
 */
public abstract class Reply {
    protected Long chatID;
    protected SendMessage sendMessage;



    public abstract SendMessage sendMsg();
}
