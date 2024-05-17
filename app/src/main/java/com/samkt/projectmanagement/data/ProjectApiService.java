package com.samkt.projectmanagement.data;

import com.samkt.projectmanagement.data.model.request.CreateProjectRequest;
import com.samkt.projectmanagement.data.model.request.SignInRequest;
import com.samkt.projectmanagement.data.model.request.SignUpRequest;
import com.samkt.projectmanagement.data.model.response.AllProjectsResponse;
import com.samkt.projectmanagement.data.model.response.CreateProjectResponse;
import com.samkt.projectmanagement.data.model.response.DeleteProjectResponse;
import com.samkt.projectmanagement.data.model.response.SignInResponse;
import com.samkt.projectmanagement.data.model.response.SignUpResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
    Call<DeleteProjectResponse> deleteProject(@Path("id") String id);

}
