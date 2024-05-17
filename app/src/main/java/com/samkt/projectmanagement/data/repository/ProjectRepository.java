package com.samkt.projectmanagement.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.samkt.projectmanagement.data.ProjectApiService;
import com.samkt.projectmanagement.data.model.request.CreateProjectRequest;
import com.samkt.projectmanagement.data.model.response.CreateProjectResponse;
import com.samkt.projectmanagement.models.PostResult;
import com.samkt.projectmanagement.models.SignUpInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectRepository {
    private final ProjectApiService apiService;


    public ProjectRepository(ProjectApiService apiService) {
        this.apiService = apiService;
    }

    public MutableLiveData<PostResult> saveProject(CreateProjectRequest createProjectRequest){
        MutableLiveData<PostResult> saveProjectResult = new MutableLiveData<>();
        apiService.createProject(createProjectRequest).enqueue(new Callback<CreateProjectResponse>() {
            @Override
            public void onResponse(@NonNull Call<CreateProjectResponse> call, @NonNull Response<CreateProjectResponse> response) {

                if (response.isSuccessful() && response.body() != null){
                    PostResult postResult = new PostResult(response.body().getMessage(),null);
                    saveProjectResult.postValue(postResult);
                }

                if (!response.isSuccessful()){
                    PostResult postResult = new PostResult(null,response.message());
                    saveProjectResult.postValue(postResult);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CreateProjectResponse> call, @NonNull Throwable t) {
                PostResult postResult = new PostResult(null,t.getMessage());
                saveProjectResult.postValue(postResult);
            }
        });
        return saveProjectResult;
    }



}
