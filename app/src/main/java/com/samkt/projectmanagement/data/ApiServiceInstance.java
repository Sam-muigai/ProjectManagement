package com.samkt.projectmanagement.data;

import com.samkt.projectmanagement.utils.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiServiceInstance {

    public static ProjectApiService getApiServices(){
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ProjectApiService.class);
    }
}
