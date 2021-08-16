package com.gorynich.educationbot.bot.handler;

import com.gorynich.educationbot.bot.reply.ReplyText;
import com.gorynich.educationbot.bot.settings.Settings;
import com.gorynich.educationbot.bot.settings.TaskTypes;
import com.gorynich.educationbot.bot.task.TaskMaker;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class Handler {
    private Update update;//приходящий от телеги Update
    private Settings settings;//настройки по-умолчанию. Меняются, если в запросе новые настройки
    private SendMessage sendMessage;

    private List<TaskMaker> listOfTasks;//массив задач с примерами и ответами

    private Long chatID;//идентификатор чата
    private String userName;//Имя пользователя
    private String incomingMessage;//сообщение в запросе

    private boolean startATask = false;
    private int correctAnswers = 0;
    private int incorrectAnswers = 0;
    private int totalQuestions = 0;

    private boolean setSettingsBoolean = false;


//    public Handler(Update update) {
//        this.update = update;
//        settings = new Settings(1, 20, 5,
//                new TaskTypes[]{TaskTypes.SUM});//ToDo: внести настройки по умолчанию
//    }

    public Handler() {
        settings = new Settings(1, 20, 5,
                new TaskTypes[]{TaskTypes.SUM});//ToDo: внести настройки по умолчанию
        sendMessage = new SendMessage();
    }

    //Получаем текущий update
    public void setUpdate(Update update){
        this.update = update;
    }

    //Обработка полученного запроса
    public SendMessage handle(){


        //Заполняем поля
        incomingMessage = update.getMessage().getText();
        chatID = update.getMessage().getChatId();
        userName = update.getMessage().getChat().getUserName();

        //Задаем клавиатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        ArrayList<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardSecondRow = new KeyboardRow();

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        keyboardFirstRow.add("начать");
        keyboardFirstRow.add("стоп");
        //keyboardSecondRow.add("условия");

        keyboardRows.add(keyboardFirstRow);
        //keyboardRows.add(keyboardSecondRow);
        replyKeyboardMarkup.setKeyboard(keyboardRows);

        //Определяем тип пришедшего сообщения:
        //сервисные команды
        //настройки
        //команда на начало работы
        //ответ на пример

        //Сервисная команда
        if (incomingMessage.startsWith("/")){
            String reply = new ServiceMessageHandler(incomingMessage, settings).handleServiceMessage();
            ReplyText replyText = new ReplyText(chatID, reply);
            sendMessage = replyText.sendMsg();
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
            return sendMessage;
        }
        //настройки
        //if ()

        //начать решение
        if (incomingMessage.equals("начать"))
        {
            listOfTasks = new ArrayList<>();
            listOfTasks = new StartSolution(settings).makeExamples();
            startATask = true;
            totalQuestions = listOfTasks.size();
            String reply = listOfTasks.get(0).getQuestion();

            ReplyText replyText = new ReplyText(chatID, reply);
            sendMessage = replyText.sendMsg();
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
            return sendMessage;
        }

        if(incomingMessage.equals("стоп")){
            listOfTasks.clear();
            startATask = false;
            ReplyText replyText = new ReplyText(chatID, "\nОстановлено решение. Для продолжения напишите " +
                    "начать");
            sendMessage = replyText.sendMsg();
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
            return sendMessage;
        }

        if (startATask)
        {
            if (!listOfTasks.isEmpty())
            {
                int response = Integer.valueOf(incomingMessage);
                if (response == listOfTasks.get(0).getSolution()){
                    correctAnswers++;
                    String reply = "Правильно!\n";
                    listOfTasks.remove(0);
                    if (!listOfTasks.isEmpty()){
                        reply += listOfTasks.get(0).getQuestion();
                        ReplyText replyText = new ReplyText(chatID, reply);
                        sendMessage = replyText.sendMsg();
                        sendMessage.setReplyMarkup(replyKeyboardMarkup);
                        return sendMessage;
                    }
                    else {
                        startATask = false;
                        reply += "Ты правильно ответил на " + correctAnswers + " из " + totalQuestions;
                        correctAnswers = 0;
                        incorrectAnswers = 0;
                        ReplyText replyText = new ReplyText(chatID, reply);
                        sendMessage = replyText.sendMsg();
                        sendMessage.setReplyMarkup(replyKeyboardMarkup);
                        return sendMessage;
                    }
                }
                else {
                    incorrectAnswers++;
                    String reply = "Неправильно!\nПравильный ответ: " + listOfTasks.get(0).getSolution() + "\n";
                    listOfTasks.remove(0);
                    if (!listOfTasks.isEmpty()){
                        reply += listOfTasks.get(0).getQuestion();
                        ReplyText replyText = new ReplyText(chatID, reply);
                        sendMessage = replyText.sendMsg();
                        sendMessage.setReplyMarkup(replyKeyboardMarkup);
                        return sendMessage;
                    }
                    else {
                        startATask = false;
                        reply += "Ты правильно ответил на " + correctAnswers + " из " + totalQuestions;
                        correctAnswers = 0;
                        incorrectAnswers = 0;
                        ReplyText replyText = new ReplyText(chatID, reply);
                        sendMessage = replyText.sendMsg();
                        sendMessage.setReplyMarkup(replyKeyboardMarkup);
                        return sendMessage;
                    }
                }
            }
        }
//
//        if (incomingMessage.startsWith("условия ")){
//            setSettings();
//            sendMessage = new SendMessage(chatID.toString(), "Настройки применены! Новые настройки \n" +
//                    settings.toString());
//            sendMessage.setReplyMarkup(replyKeyboardMarkup);
//            return sendMessage;
//        }

        if (incomingMessage.startsWith("условия ")){
            setSettingsBoolean = true;
            sendMessage = new SendMessage(chatID.toString(), "" +
                    "Введите через пробел минимальное число," +
                    " максимальное число и количество примеров, а также " +
                    "выберите действие");
        }
        if (setSettingsBoolean)
        {

            setSettingsBoolean = false;
        }


        //Вызываем соответствующий метод

        sendMessage = new SendMessage(chatID.toString(), "Я вас совершенно не понял. Пожалуйста," +
                " напишите Ваш запрос ещё раз");
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }

    //Устанавливаем настройки, пришедшие в сообщении
    private void setSettings(){
        incomingMessage = incomingMessage.replace("условия ", "");
        //Список арифметических действий, влкючаемых в примеры
        String[] actionsToMake = incomingMessage.split(" ");
        List<TaskTypes> taskTypesList = new ArrayList<>();
        for (int i = 3; i < actionsToMake.length; i++) {
            if (actionsToMake[i].equals("сумма") || actionsToMake[i].equals("Сумма")){
                taskTypesList.add(TaskTypes.SUM);
            }
            else if(actionsToMake[i].equals("вычитание") || actionsToMake[i].equals("Вычитание")){
                taskTypesList.add(TaskTypes.SUBTRACTION);
            }
            else if(actionsToMake[i].equals("умножение") || actionsToMake[i].equals("Умножение")){
                taskTypesList.add(TaskTypes.MULTIPLY);
            }
            else if(actionsToMake[i].equals("деление") || actionsToMake[i].equals("Деление")){
                taskTypesList.add(TaskTypes.DIVISION);
            }
        }
        settings = new Settings(Integer.valueOf(actionsToMake[0]), //минимальное значение
                                Integer.valueOf(actionsToMake[1]), //максимальное значение
                                Integer.valueOf(actionsToMake[2]), //количество примеров
                //(TaskTypes[]) listOfTasks.toArray()                //типы примеров
                taskTypesList.toArray(new TaskTypes[taskTypesList.size()]));
    }

//    //Создаем задачу для решения
//    private void makeNewTask(){
//        //Берем типы примеров и их количество из настроек
//        //Заполняем listOfTasks нужным количеством объектов TaskMaker
//        for (int i = 0; i < settings.getNumberOfExamples(); i++) {
//            //ToDo реализовать случайный выбор действия для пример
//            //ToDo пока только сложение
//            listOfTasks.add(new TaskMaker(settings.getMinimumValue(),
//                    settings.getMaximumValue(),
//                    TaskTypes.SUM));//ПЕРЕДЕЛАТЬ!!!!! Дебаг!
//        }
//    }


    //Подготовка ответа на запрос
    public SendMessage sendMsg(){

        return null;
    }

}
