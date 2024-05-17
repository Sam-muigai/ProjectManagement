package com.samkt.projectmanagement.ui.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.samkt.projectmanagement.data.model.request.SignInRequest;
import com.samkt.projectmanagement.data.repository.AuthRepository;
import com.samkt.projectmanagement.models.SignInInfo;

public class SignInViewModel extends ViewModel {
    private final AuthRepository authRepository;

    public SignInViewModel(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }
    public LiveData<SignInInfo> signUpUser(
            String email,
            String password
    ) {
        SignInRequest signInRequest = new SignInRequest(
                email, password
        );
        return authRepository.signInUser(signInRequest);
    }

}
