package com.samkt.projectmanagement.data;

import com.samkt.projectmanagement.data.model.request.CreateProjectRequest;
import com.samkt.projectmanagement.data.model.request.CreateTaskRequest;
import com.samkt.projectmanagement.data.model.request.SignInRequest;
import com.samkt.projectmanagement.data.model.request.SignUpRequest;
import com.samkt.projectmanagement.data.model.request.UpdateProjectRequest;
import com.samkt.projectmanagement.data.model.response.AllProjectsResponse;
import com.samkt.projectmanagement.data.model.response.CreateProjectResponse;
import com.samkt.projectmanagement.data.model.response.CreateTaskResponse;
import com.samkt.projectmanagement.data.model.response.DeleteResponse;
import com.samkt.projectmanagement.data.model.response.SignInResponse;
import com.samkt.projectmanagement.data.model.response.SignUpResponse;
import com.samkt.projectmanagement.data.model.response.TasksResponse;
import com.samkt.projectmanagement.data.model.response.UpdateResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProjectApiService {
    @POST("register")
    Call<SignUpResponse> signUpUser(@Body SignUpRequest signUpRequest);

    @POST("login")
    Call<SignInResponse> signInUser(@Body SignInRequest signInRequest);

    @POST("projects")
    Call<CreateProjectResponse> createProject(@Body CreateProjectRequest createProjectRequest);

    @GET("projects")
    Call<AllProjectsResponse> getAllProject();

    @DELETE("projects/{id}")
    Call<DeleteResponse> deleteProject(@Path("id") String id);

    @PUT("projects/{id}")
    Call<UpdateResponse> updateProject(@Path("id") String id, @Body CreateProjectRequest createProjectRequest);

    @POST("project-tasks")
    Call<CreateTaskResponse> createTask(@Body CreateTaskRequest createTaskRequest);

    @GET("project-tasks/{id}")
    Call<TasksResponse> getAllTask(@Path("id") String projectId);

    @DELETE("project-tasks/{id}")
    Call<DeleteResponse> deleteTask(@Path("id") String taskId);

    @PUT("project-tasks/{id}")
    Call<UpdateResponse> updateTask(@Path("id") String taskId, @Body UpdateProjectRequest updateProjectRequest);

}
