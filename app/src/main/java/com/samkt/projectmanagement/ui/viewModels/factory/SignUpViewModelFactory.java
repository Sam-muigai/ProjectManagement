package com.samkt.projectmanagement.ui.viewModels.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.samkt.projectmanagement.data.repository.AuthRepository;
import com.samkt.projectmanagement.ui.viewModels.SignUpViewModel;

public class SignUpViewModelFactory  implements ViewModelProvider.Factory {

    private final AuthRepository authRepository;

    public SignUpViewModelFactory(AuthRepository authRepository){
        this.authRepository = authRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SignUpViewModel.class)) {
            return (T) new SignUpViewModel(authRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
