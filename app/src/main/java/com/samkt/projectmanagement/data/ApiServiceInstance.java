package com.samkt.projectmanagement.data;

import androidx.annotation.NonNull;

import com.samkt.projectmanagement.data.preferences.ProjectPreferences;
import com.samkt.projectmanagement.utils.Constants;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiServiceInstance {
    private final ProjectPreferences projectPreferences;

    public ApiServiceInstance(ProjectPreferences projectPreferences) {
        this.projectPreferences = projectPreferences;
    }

    public ProjectApiService getApiServices(Boolean tokenRequired){
        Interceptor tokenInterceptor = new Interceptor() {
            @NonNull
            @Override
            public Response intercept(Chain chain) throws IOException {
                String token;
                if( projectPreferences.getUserToken() == null){
                    token = "";
                }else {
                    token = projectPreferences.getUserToken().trim();
                }
                Request request = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + token)
                        .build();
                return chain.proceed(request);
            }
        };

        OkHttpClient tokenHttpClient = new OkHttpClient()
                .newBuilder()
                .addInterceptor(tokenInterceptor)
                .connectTimeout(Constants.TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(Constants.TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(Constants.TIME_OUT, TimeUnit.SECONDS)
                .build();

        OkHttpClient httpClient = new OkHttpClient()
                .newBuilder()
                .connectTimeout(Constants.TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(Constants.TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(Constants.TIME_OUT, TimeUnit.SECONDS)
                .build();

        if (tokenRequired){
            return new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(tokenHttpClient)
                    .build()
                    .create(ProjectApiService.class);
        }else {
            return new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .build()
                    .create(ProjectApiService.class);
        }
    }
}
