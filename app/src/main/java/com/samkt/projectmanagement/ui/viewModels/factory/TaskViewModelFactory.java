package com.samkt.projectmanagement.ui.viewModels.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.samkt.projectmanagement.data.repository.TaskRepository;
import com.samkt.projectmanagement.ui.viewModels.HomeViewModel;
import com.samkt.projectmanagement.ui.viewModels.TaskViewModel;

public class TaskViewModelFactory implements ViewModelProvider.Factory{
    private final TaskRepository taskRepository;

    public TaskViewModelFactory(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TaskViewModel.class)) {
            return (T) new TaskViewModel(taskRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
