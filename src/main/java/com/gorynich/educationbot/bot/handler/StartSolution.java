package com.gorynich.educationbot.bot.handler;

import com.gorynich.educationbot.bot.settings.Settings;
import com.gorynich.educationbot.bot.settings.TaskTypes;
import com.gorynich.educationbot.bot.task.TaskMaker;

import java.util.ArrayList;
import java.util.List;

public class StartSolution {
    private List<TaskMaker> currentTasks;
    private int minimumValue;
    private int maximumValue;
    private int numberOfSolutions;
    private TaskTypes[] taskTypes;

    private Settings settings;

    public StartSolution(Settings settings) {
        this.settings = settings;
        currentTasks = new ArrayList<>();
        this.minimumValue = settings.getMinimumValue();
        this.maximumValue = settings.getMaximumValue();
        this.numberOfSolutions = settings.getNumberOfExamples();
    }

    public List<TaskMaker> makeExamples()
    {
        currentTasks.clear();
        for (int i = 0; i < numberOfSolutions; i++) {
            //ToDo реализовать различные типы вычислений и их корректный выбор
            currentTasks.add(
                    new TaskMaker(minimumValue,
                            maximumValue,
                            settings.getTaskTypes()[0])
            );
        }
        return currentTasks;
    }
}
