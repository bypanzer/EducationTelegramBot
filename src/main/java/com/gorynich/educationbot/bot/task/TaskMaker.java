package com.gorynich.educationbot.bot.task;

import com.gorynich.educationbot.bot.settings.Settings;
import com.gorynich.educationbot.bot.settings.TaskTypes;

public class TaskMaker {
    private int firstNumber;
    private int secondNumber;
    private int solution;
    private String question;
    private TaskTypes task;

    public TaskMaker(int minimumValue,
                     int maximumValue,
                     TaskTypes taskType) {
        firstNumber = (int) ((Math.random() * (maximumValue - minimumValue)) +
                minimumValue);
        secondNumber = (int) ((Math.random() * (maximumValue - minimumValue)) +
                minimumValue);
        task = taskType;
        switch (taskType){
            case SUM:
                solution = firstNumber + secondNumber;
                break;
            case SUBTRACTION:
                if (firstNumber > secondNumber){
                    solution = firstNumber - secondNumber;
                }
                else {
                    solution = secondNumber - firstNumber;
                }
                break;
            case MULTIPLY:
                solution = firstNumber * secondNumber;
                break;
            case DIVISION:
                //ToDo: сделать корректное деление с проверкой значений и нацело
                solution = firstNumber / secondNumber;
                break;
        }
    }

    //геттеры для чисел и ответа

    public int getFirstNumber() {
        return firstNumber;
    }

    public int getSecondNumber() {
        return secondNumber;
    }

    public int getSolution() {
        return solution;
    }

    public String getQuestion() {
        StringBuilder str = new StringBuilder();
        str.append(firstNumber);
        str.append(" ");
        switch (task){
            case SUM:
                str.append("+ ");
                break;
            case SUBTRACTION:
                str.append("- ");
                break;
            case MULTIPLY:
                str.append("* ");
                break;
            case DIVISION:
                str.append("/ ");
                break;
        }
        str.append(secondNumber);
        return str.toString();
    }
}
