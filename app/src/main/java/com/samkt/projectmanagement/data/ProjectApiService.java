package com.samkt.projectmanagement.data;

import com.samkt.projectmanagement.data.model.request.SignUpRequest;
import com.samkt.projectmanagement.data.model.response.SignUpResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ProjectApiService {

    @POST("register")
    Call<SignUpResponse> signUpUser(@Body SignUpRequest signUpRequest);

}
