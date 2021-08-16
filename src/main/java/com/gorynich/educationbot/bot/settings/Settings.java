package com.gorynich.educationbot.bot.settings;

public class Settings {
    private TaskTypes[] taskTypes;
    private int minimumValue;
    private int maximumValue;
    private int numberOfExamples;

    public Settings(int minimumValue, int maximumValue, int numberOfExamples, TaskTypes[] taskTypes) {
        this.minimumValue = minimumValue;
        this.maximumValue = maximumValue;
        this.numberOfExamples = numberOfExamples;
        this.taskTypes = new TaskTypes[taskTypes.length];
        //this.taskTypes = new TaskTypes[taskTypes.length]
        for (int i = 0; i < taskTypes.length; i++) {
            this.taskTypes[i] = taskTypes[i];
        }
    }

    public TaskTypes[] getTaskTypes() {
        return taskTypes;
    }

    public int getMinimumValue() {
        return minimumValue;
    }

    public int getMaximumValue() {
        return maximumValue;
    }

    public int getNumberOfExamples() {
        return numberOfExamples;
    }

    @Override
    public String toString() {
        String tasks = " ";
        for (int i = 0; i < taskTypes.length; i++) {
            tasks += taskTypes[i].toString() + " ";
        }
        return minimumValue + " " +
                maximumValue + " " +
                numberOfExamples + " " +
                tasks;
    }
}
