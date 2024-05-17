package com.samkt.projectmanagement.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.samkt.projectmanagement.R;
import com.samkt.projectmanagement.data.ApiServiceInstance;
import com.samkt.projectmanagement.data.model.response.SignUpResponse;
import com.samkt.projectmanagement.data.repository.AuthRepository;
import com.samkt.projectmanagement.databinding.FragmentSignUpBinding;
import com.samkt.projectmanagement.ui.viewModels.SignUpViewModel;
import com.samkt.projectmanagement.ui.viewModels.factory.SignUpViewModelFactory;
import com.samkt.projectmanagement.utils.Result;

import java.util.Objects;

import timber.log.Timber;


public class SignUpFragment extends Fragment {

    private FragmentSignUpBinding binding;
    private SignUpViewModel signUpViewModel;

    private String userName, email, phoneNumber, password, confirmPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SignUpViewModelFactory signUpViewModelFactory = new SignUpViewModelFactory(new AuthRepository(ApiServiceInstance.getApiServices()));
        signUpViewModel = new ViewModelProvider(this, signUpViewModelFactory).get(SignUpViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnSignUp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!isAllFieldsFilled()) {
                            Toast.makeText(requireContext(), "All the fields must be filled", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (!passwordMatches()) {
                            Toast.makeText(requireContext(), "Password and confirm password do not match", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        // TODO: 5/17/2024 Sign Up User
                        userName = Objects.requireNonNull(binding.etUsername.getText()).toString();
                        phoneNumber = Objects.requireNonNull(binding.etPhoneNumber.getText()).toString();
                        email = Objects.requireNonNull(binding.etEmail.getText()).toString();
                        password = Objects.requireNonNull(binding.etPassword.getText()).toString();
                        confirmPassword = Objects.requireNonNull(binding.etConfirmPassword.getText()).toString();
                        signUpLoading();
                        Timber.d("SignUser fragment");
                         signUpUser(
                                userName, phoneNumber, email, password, confirmPassword
                        );
                    }
                }
        );


    }

    private void signUpUser(
            String userName,
            String phone,
            String email,
            String password,
            String confirmPassword
    ) {
        signUpViewModel.signUpUser(userName, phone, email, password, confirmPassword).observe(getViewLifecycleOwner(), signUpInfo -> {
            if (signUpInfo.getErrorMessage() != null) {
                restoreSignUpState();
                Toast.makeText(requireContext(), signUpInfo.getErrorMessage(), Toast.LENGTH_SHORT).show();
                return;
            }
            if (signUpInfo.getSuccessMessage() != null){
                navigateToSignIn();
                Toast.makeText(requireContext(), signUpInfo.getSuccessMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Boolean isAllFieldsFilled() {
        return !Objects.requireNonNull(binding.etConfirmPassword.getText()).toString().isEmpty()
                && !Objects.requireNonNull(binding.etPassword.getText()).toString().isEmpty()
                && !Objects.requireNonNull(binding.etUsername.getText()).toString().isEmpty()
                && !Objects.requireNonNull(binding.etPhoneNumber.getText()).toString().isEmpty();
    }

    private Boolean passwordMatches() {
        return Objects.requireNonNull(binding.etPassword.getText()).toString().equals(
                Objects.requireNonNull(binding.etConfirmPassword.getText()).toString()
        );
    }

    private void signUpLoading() {
        binding.btnSignUp.setVisibility(View.GONE);
        binding.pbLoading.setVisibility(View.VISIBLE);
    }

    private void restoreSignUpState() {
        binding.btnSignUp.setVisibility(View.VISIBLE);
        binding.pbLoading.setVisibility(View.GONE);
    }

    private void navigateToSignIn(){
        NavOptions navOptions = new NavOptions.Builder()
                .setPopUpTo(R.id.signInFragment, true)
                .build();
        NavHostFragment.findNavController(this).navigate(
                R.id.action_signUpFragment_to_signInFragment,
                null,
                navOptions
        );
    }

}