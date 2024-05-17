package com.samkt.projectmanagement.ui.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.samkt.projectmanagement.data.model.request.SignUpRequest;
import com.samkt.projectmanagement.data.model.response.SignUpResponse;
import com.samkt.projectmanagement.data.repository.AuthRepository;
import com.samkt.projectmanagement.models.SignUpInfo;
import com.samkt.projectmanagement.utils.Result;

import timber.log.Timber;

public class SignUpViewModel extends ViewModel {
    private final AuthRepository authRepository;

    private MutableLiveData<SignUpInfo> _signUpInfo;
    public SignUpViewModel(AuthRepository authRepository){
        this.authRepository = authRepository;
    }

    public LiveData<SignUpInfo> signUpUser(
            String userName,
            String phone,
            String email,
            String password,
            String confirmPassword
    ){
        if (_signUpInfo == null){
            SignUpRequest signUpRequest = new SignUpRequest(
                    userName,email,phone,password,confirmPassword
            );
            Timber.d("SignUser viewModel: Success");
            _signUpInfo = authRepository.signUpUser(signUpRequest);
        }
        return _signUpInfo;
    }

}
