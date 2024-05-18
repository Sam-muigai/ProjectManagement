package com.samkt.projectmanagement.ui;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import androidx.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.samkt.projectmanagement.R;
import com.samkt.projectmanagement.data.ApiServiceInstance;
import com.samkt.projectmanagement.data.preferences.ProjectPreferences;
import com.samkt.projectmanagement.data.repository.ProjectRepository;
import com.samkt.projectmanagement.ui.viewModels.HomeViewModel;
import com.samkt.projectmanagement.ui.viewModels.factory.HomeViewModelFactory;

public class SplashFragment extends Fragment {

    private HomeViewModel homeViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        ProjectPreferences projectPreferences = new ProjectPreferences(preferences);
        ApiServiceInstance apiServiceInstance = new ApiServiceInstance(projectPreferences);
        HomeViewModelFactory homeViewModelFactory = new HomeViewModelFactory(new ProjectRepository(apiServiceInstance.getApiServices(true)));
        homeViewModel = new ViewModelProvider(this, homeViewModelFactory).get(HomeViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getProjects();

    }

    private void getProjects() {
        homeViewModel.getProjects().observe(
                getViewLifecycleOwner(), info -> {
                    if (info.getErrorMessage() != null) {
                        navigateLogin();
                        return;
                    }
                    if (info.getProjects() != null) {
                        navigateHome();
                    }
                }
        );
    }

    void navigateHome() {
        NavOptions navOptions = new NavOptions.Builder()
                .setPopUpTo(R.id.splashFragment, true)
                .build();
        NavHostFragment.findNavController(this).navigate(
                R.id.action_splashFragment_to_homeFragment,
                null,
                navOptions
        );
    }

    void navigateLogin() {
        NavOptions navOptions = new NavOptions.Builder()
                .setPopUpTo(R.id.splashFragment, true)
                .build();
        NavHostFragment.findNavController(this).navigate(
                R.id.action_splashFragment_to_signInFragment,
                null,
                navOptions
        );
    }
}