package com.samkt.projectmanagement.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.samkt.projectmanagement.databinding.FragmentSignUpBinding;


public class SignUpFragment extends Fragment {

    private FragmentSignUpBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignUpBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnSignUp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!isAllFieldsFilled()){
                            Toast.makeText(requireContext(), "All the fields must be filled", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (!passwordMatches()){
                            Toast.makeText(requireContext(), "Password and confirm password do not match", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        // TODO: 5/17/2024 Sign Up User
                    }
                }
        );
    }

    private Boolean isAllFieldsFilled(){
        return !binding.etConfirmPassword.getText().toString().isEmpty()
                && !binding.etPassword.getText().toString().isEmpty()
                && !binding.etUsername.getText().toString().isEmpty();
    }

    private Boolean passwordMatches(){
        return binding.etPassword.getText().toString().equals(
                binding.etConfirmPassword.getText().toString()
        );
    }

}