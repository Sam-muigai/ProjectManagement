package com.samkt.projectmanagement.ui.viewModels.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.samkt.projectmanagement.data.repository.AuthRepository;
import com.samkt.projectmanagement.data.repository.ProjectRepository;
import com.samkt.projectmanagement.ui.viewModels.HomeViewModel;
import com.samkt.projectmanagement.ui.viewModels.SignInViewModel;

public class HomeViewModelFactory implements ViewModelProvider.Factory{
    private final ProjectRepository projectRepository;

    public HomeViewModelFactory(ProjectRepository projectRepository){
        this.projectRepository = projectRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            return (T) new HomeViewModel(projectRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
