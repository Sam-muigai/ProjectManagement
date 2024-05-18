package com.samkt.projectmanagement.ui.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.samkt.projectmanagement.data.model.request.CreateTaskRequest;
import com.samkt.projectmanagement.data.model.request.UpdateProjectRequest;
import com.samkt.projectmanagement.data.model.response.Task;
import com.samkt.projectmanagement.data.repository.TaskRepository;
import com.samkt.projectmanagement.models.AllTasks;
import com.samkt.projectmanagement.models.DeleteResult;
import com.samkt.projectmanagement.models.PostResult;
import com.samkt.projectmanagement.models.UpdateResult;

import java.util.List;

public class TaskViewModel extends ViewModel {

    private final TaskRepository repository;

    public TaskViewModel(TaskRepository repository) {
        this.repository = repository;
    }

    public LiveData<PostResult> saveTask(
            String id,
            String name,
            String deadline
    ){
        CreateTaskRequest taskRequest = new CreateTaskRequest(
                id,name,deadline
        );
        return repository.saveTask(taskRequest);
    }

    public LiveData<AllTasks> getTask(
            String projectId
    ){
        return repository.getTasks(projectId);
    }

    public LiveData<DeleteResult> deleteTask(
        String taskId
    ){
        return repository.deleteTask(taskId);
    }

    public LiveData<UpdateResult> updateTask(String id,   String name, String deadline){
        UpdateProjectRequest updateProjectRequest = new UpdateProjectRequest(name,deadline);
        return repository.updateTask(id,updateProjectRequest);
    }

}
