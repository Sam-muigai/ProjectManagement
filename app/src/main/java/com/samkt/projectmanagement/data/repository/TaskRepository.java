package com.samkt.projectmanagement.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.samkt.projectmanagement.data.ProjectApiService;
import com.samkt.projectmanagement.data.model.request.CreateTaskRequest;
import com.samkt.projectmanagement.data.model.response.CreateTaskResponse;
import com.samkt.projectmanagement.data.model.response.Task;
import com.samkt.projectmanagement.data.model.response.TasksResponse;
import com.samkt.projectmanagement.models.AllTasks;
import com.samkt.projectmanagement.models.PostResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskRepository {
    private final ProjectApiService apiService;


    public TaskRepository(ProjectApiService apiService) {
        this.apiService = apiService;
    }

    public MutableLiveData<PostResult> saveTask(CreateTaskRequest createTaskRequest){
        MutableLiveData<PostResult> saveTaskResult = new MutableLiveData<>();
        apiService.createTask(createTaskRequest).enqueue(new Callback<CreateTaskResponse>() {
            @Override
            public void onResponse(@NonNull Call<CreateTaskResponse> call, @NonNull Response<CreateTaskResponse> response) {
                if (response.isSuccessful() && response.body() != null){
                    CreateTaskResponse createTaskResponse = response.body();
                    saveTaskResult.postValue(new PostResult(createTaskResponse.getMessage(),null));
                }

                if (!response.isSuccessful()){
                    saveTaskResult.postValue(new PostResult(null,response.message()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<CreateTaskResponse> call, @NonNull Throwable t) {
                saveTaskResult.postValue(new PostResult(null,t.getMessage()));
            }
        });
        return saveTaskResult;
    }


    public MutableLiveData<AllTasks> getTasks(String projectId){
        MutableLiveData<AllTasks> getTasksResult = new MutableLiveData<>();

        apiService.getAllTask(projectId).enqueue(new Callback<TasksResponse>() {
            @Override
            public void onResponse(@NonNull Call<TasksResponse> call, @NonNull Response<TasksResponse> response) {
                if (response.isSuccessful() && response.body() != null){
                    List<Task> tasks = response.body().getTasks();
                    getTasksResult.postValue(new AllTasks(null,tasks));
                }
                if (!response.isSuccessful()){
                    getTasksResult.postValue(new AllTasks(response.message(),null));
                }
            }

            @Override
            public void onFailure(@NonNull Call<TasksResponse> call, @NonNull Throwable t) {
                getTasksResult.postValue(new AllTasks(t.getMessage(),null));
            }
        });
        return getTasksResult;
    }


}
