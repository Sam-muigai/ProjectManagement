package com.samkt.projectmanagement.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.samkt.projectmanagement.data.ProjectApiService;
import com.samkt.projectmanagement.data.model.request.CreateTaskRequest;
import com.samkt.projectmanagement.data.model.request.UpdateProjectRequest;
import com.samkt.projectmanagement.data.model.response.CreateTaskResponse;
import com.samkt.projectmanagement.data.model.response.DeleteResponse;
import com.samkt.projectmanagement.data.model.response.Task;
import com.samkt.projectmanagement.data.model.response.TasksResponse;
import com.samkt.projectmanagement.data.model.response.UpdateResponse;
import com.samkt.projectmanagement.models.AllTasks;
import com.samkt.projectmanagement.models.DeleteResult;
import com.samkt.projectmanagement.models.PostResult;
import com.samkt.projectmanagement.models.UpdateResult;

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

    public MutableLiveData<DeleteResult> deleteTask(String taskId){
        MutableLiveData<DeleteResult> deleteTask = new MutableLiveData<>();

        apiService.deleteTask(taskId).enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(@NonNull Call<DeleteResponse> call, @NonNull Response<DeleteResponse> response) {

                if (response.isSuccessful() && response.body() != null){
                    DeleteResponse deleteResponse = response.body();
                    deleteTask.postValue(new DeleteResult(deleteResponse.getMessage(),null));
                }

                if (!response.isSuccessful()){
                    deleteTask.postValue(new DeleteResult(null,response.message()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<DeleteResponse> call, @NonNull Throwable t) {
                deleteTask.postValue(new DeleteResult(null,t.getMessage()));
            }
        });
        return deleteTask;
    }

    public MutableLiveData<UpdateResult> updateTask(String id, UpdateProjectRequest updateProjectRequest){
        MutableLiveData<UpdateResult> updateResult = new MutableLiveData<>();

        apiService.updateTask(id, updateProjectRequest).enqueue(new Callback<UpdateResponse>() {
            @Override
            public void onResponse(@NonNull Call<UpdateResponse> call, @NonNull Response<UpdateResponse> response) {
                if (response.isSuccessful() && response.body() != null){
                    UpdateResponse updateResponse = response.body();
                    updateResult.postValue(new UpdateResult(updateResponse.getMessage(),null));
                }

                if (!response.isSuccessful()){
                    updateResult.postValue(new UpdateResult(null,response.message()));
                }
            }
            @Override
            public void onFailure(@NonNull Call<UpdateResponse> call, @NonNull Throwable t) {
                updateResult.postValue(new UpdateResult(null,t.getMessage()));
            }
        });
        return updateResult;
    }


}
