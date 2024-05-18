package com.samkt.projectmanagement.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.samkt.projectmanagement.R;
import com.samkt.projectmanagement.databinding.FragmentTaskBinding;


public class TaskFragment extends Fragment {

    private FragmentTaskBinding binding;
    private String projectId,projectName;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            projectName = getArguments().getString("name");
            projectId = getArguments().getString("id");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTaskBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String title = "TASKS FOR " + projectName + " PROJECT";
        binding.tvTitle.setText(title);
    }
}