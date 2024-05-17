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
import android.widget.Toast;

import com.samkt.projectmanagement.R;
import com.samkt.projectmanagement.data.ApiServiceInstance;
import com.samkt.projectmanagement.data.preferences.ProjectPreferences;
import com.samkt.projectmanagement.data.repository.AuthRepository;
import com.samkt.projectmanagement.databinding.FragmentSignInBinding;
import com.samkt.projectmanagement.ui.viewModels.SignInViewModel;
import com.samkt.projectmanagement.ui.viewModels.factory.SignInViewModelFactory;

import java.util.Objects;


public class SignInFragment extends Fragment {


    private FragmentSignInBinding binding;
    private SignInViewModel signInViewModel;
    private String emailOrPassword,password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        ProjectPreferences projectPreferences = new ProjectPreferences(preferences);
        AuthRepository authRepository = new AuthRepository(ApiServiceInstance.getApiServices(),projectPreferences);
        SignInViewModelFactory signInViewModelFactory = new SignInViewModelFactory(authRepository);
        signInViewModel = new ViewModelProvider(this,signInViewModelFactory).get(SignInViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignInBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!allFieldsFilled()){
                    Toast.makeText(requireContext(),"All fields must be filled",Toast.LENGTH_LONG).show();
                    return;
                }
                emailOrPassword = Objects.requireNonNull(binding.etUsernameOrEmail.getText()).toString();
                password = Objects.requireNonNull(binding.etPassword.getText()).toString();
                startSignIn();
                signInUser(emailOrPassword,password);
            }
        });
    }


    private Boolean allFieldsFilled(){
        return !Objects.requireNonNull(binding.etPassword.getText()).toString().isEmpty()
                && !Objects.requireNonNull(binding.etUsernameOrEmail.getText()).toString().isEmpty();
    }

    private void signInUser(
            String email,
            String password
    ){
        signInViewModel.signUpUser(email, password).observe(getViewLifecycleOwner(),signInInfo -> {
            if (signInInfo.getErrorMessage() != null) {
                restoreSignInState();
                Toast.makeText(requireContext(), signInInfo.getErrorMessage(), Toast.LENGTH_SHORT).show();
                return;
            }
            if (signInInfo.getSuccessMessage() != null){
                navigateToHomeScreen();
                Toast.makeText(requireContext(), signInInfo.getSuccessMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void restoreSignInState() {
        binding.btnLogin.setVisibility(View.VISIBLE);
        binding.pbLoading.setVisibility(View.GONE);
    }

    private void startSignIn() {
        binding.btnLogin.setVisibility(View.GONE);
        binding.pbLoading.setVisibility(View.VISIBLE);
    }

    private void navigateToHomeScreen() {
        NavOptions navOptions = new NavOptions.Builder()
                .setPopUpTo(R.id.signInFragment, true)
                .build();
        NavHostFragment.findNavController(this).navigate(
                R.id.action_signInFragment_to_homeFragment,
                null,
                navOptions
        );
    }
}