package com.samkt.projectmanagement.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.samkt.projectmanagement.data.ProjectApiService;
import com.samkt.projectmanagement.data.model.request.SignInRequest;
import com.samkt.projectmanagement.data.model.request.SignUpRequest;
import com.samkt.projectmanagement.data.model.response.SignInResponse;
import com.samkt.projectmanagement.data.model.response.SignUpResponse;
import com.samkt.projectmanagement.data.preferences.ProjectPreferences;
import com.samkt.projectmanagement.models.PostResult;
import com.samkt.projectmanagement.models.SignUpInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class AuthRepository {
    private final ProjectApiService apiService;
    private final ProjectPreferences preferences;

    public AuthRepository(ProjectApiService apiService, ProjectPreferences preferences) {
        this.apiService = apiService;
        this.preferences = preferences;
    }


    public MutableLiveData<SignUpInfo> signUpUser(SignUpRequest signUpRequest) {
        MutableLiveData<SignUpInfo> signUpResult = new MutableLiveData<>();
        Timber.d("SignUser repository");
        apiService.signUpUser(signUpRequest).enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(@NonNull Call<SignUpResponse> call, @NonNull Response<SignUpResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Timber.d("SignUser repository: Success");
                    SignUpResponse signUpResponse = response.body();
                    signUpResult.setValue(new SignUpInfo(signUpResponse.getMessage(), null));
                }

                if (!response.isSuccessful()) {
                    Timber.d("SignUser repository: %s", response.message());
                    signUpResult.setValue(new SignUpInfo(null, response.message()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<SignUpResponse> call, @NonNull Throwable t) {
                Timber.d("SignUser repository: %s", t.getLocalizedMessage());
                signUpResult.setValue(new SignUpInfo(null, t.getLocalizedMessage()));
            }
        });
        return signUpResult;
    }

    public MutableLiveData<PostResult> signInUser(SignInRequest signInRequest) {
        MutableLiveData<PostResult> signInResult = new MutableLiveData<>();
        Timber.d("SignUser repository");
        apiService.signInUser(signInRequest).enqueue(new Callback<SignInResponse>() {
            @Override
            public void onResponse(@NonNull Call<SignInResponse> call, @NonNull Response<SignInResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Timber.d("SignUser repository: Success");
                    SignInResponse signInResponse = response.body();
                    String userToken = signInResponse.getData().getAccessToken();
                    preferences.saveUserToken(userToken);
                    signInResult.setValue(new PostResult(signInResponse.getMessage(), null));
                }
                if (!response.isSuccessful()) {
                    Timber.d("SignUser repository: %s", response.message());
                    signInResult.setValue(new PostResult(null, response.message()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<SignInResponse> call, @NonNull Throwable t) {
                Timber.d("SignUser repository: %s", t.getLocalizedMessage());
                signInResult.setValue(new PostResult(null, t.getLocalizedMessage()));
            }
        });
        return signInResult;
    }

}
