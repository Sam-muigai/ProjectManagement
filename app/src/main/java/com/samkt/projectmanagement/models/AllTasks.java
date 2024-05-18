package com.samkt.projectmanagement.models;

import com.samkt.projectmanagement.data.model.response.Project;
import com.samkt.projectmanagement.data.model.response.Task;

import java.util.List;

public class AllTasks {
    private String errorMessage;
    private List<Task> tasks;

    public AllTasks(String errorMessage, List<Task> tasks) {
        this.errorMessage = errorMessage;
        this.tasks = tasks;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
