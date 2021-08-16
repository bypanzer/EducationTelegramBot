package com.gorynich.educationbot.bot.handler;

import com.gorynich.educationbot.bot.settings.Settings;
import org.telegram.telegrambots.meta.api.objects.Update;

public class ServiceMessageHandler {
    private String serviceMessage;
    private Settings settings;

    public ServiceMessageHandler(String serviceMessage, Settings settings) {
        this.serviceMessage = serviceMessage;
        this.settings = settings;
    }

    public String handleServiceMessage(){

        serviceMessage = serviceMessage.replace("/", "");
        if (serviceMessage.equals("start") || serviceMessage.equals("старт")){
            //Todo:команда на начало работы с ботом
            //Todo: Обнулить настройки по-умолчанию и ждать новых
        }
        else if(serviceMessage.equals("help") || serviceMessage.equals("помощь")){
            //ToDo:Отображаем сообщение Help
            return "Для начала работы прищлите настройки";
        }
        else if(serviceMessage.equals("settings") || serviceMessage.equals("настройки")){
            //ToDo:Возвращаем текущие настройки
            return settings.toString();
        }
        else {
            //Неверная сервисная команда. Повторите ввод
            return "Неверная сервисная команда управления. Пожалуйста, повторите ввод!";
        }

        return null;
    }

}
