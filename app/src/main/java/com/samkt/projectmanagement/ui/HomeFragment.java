package com.samkt.projectmanagement.ui;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.samkt.projectmanagement.R;
import com.samkt.projectmanagement.data.ApiServiceInstance;
import com.samkt.projectmanagement.data.preferences.ProjectPreferences;
import com.samkt.projectmanagement.data.repository.ProjectRepository;
import com.samkt.projectmanagement.databinding.FragmentHomeBinding;
import com.samkt.projectmanagement.ui.viewModels.HomeViewModel;
import com.samkt.projectmanagement.ui.viewModels.factory.HomeViewModelFactory;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private BottomSheetDialog dialog;
    private HomeViewModel homeViewModel;

    private EditText projectName,projectDescription;
    private ProgressBar pbSavingProject;
    private Button btnSaveTask;
    private String name,description;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        ProjectPreferences projectPreferences = new ProjectPreferences(preferences);
        ApiServiceInstance apiServiceInstance = new ApiServiceInstance(projectPreferences);

        HomeViewModelFactory homeViewModelFactory = new HomeViewModelFactory(new ProjectRepository(apiServiceInstance.getApiServices(true)));
        homeViewModel = new ViewModelProvider(this,homeViewModelFactory).get(HomeViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dialog = new BottomSheetDialog(requireContext());

        binding.fabAdd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAddProjectDialog();
                    }
                }
        );
    }

    private void saveProject(
            String name,
            String description
    ){
        homeViewModel.saveTask(name, description).observe(getViewLifecycleOwner(),postResult -> {
            if (postResult.getErrorMessage() != null) {
                restoreHomeUiState();
                Toast.makeText(requireContext(), postResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
                return;
            }
            if (postResult.getSuccessMessage() != null){
                if (dialog.isShowing()){
                    dialog.cancel();
                }
                Toast.makeText(requireContext(), postResult.getSuccessMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void restoreHomeUiState() {
        btnSaveTask.setVisibility(View.VISIBLE);
        pbSavingProject.setVisibility(View.GONE);
    }

    private void savingProject(){
        btnSaveTask.setVisibility(View.GONE);
        pbSavingProject.setVisibility(View.VISIBLE);
    }

    private void showAddProjectDialog(){
        if (!dialog.isShowing()){
            dialog.setContentView(R.layout.add_project_dialog);
            btnSaveTask = dialog.findViewById(R.id.btnAddProject);
            projectName = dialog.findViewById(R.id.etProjectName);
            pbSavingProject = dialog.findViewById(R.id.pbSavingProject);
            projectDescription = dialog.findViewById(R.id.etProjectDescription);

            if (btnSaveTask != null && projectName != null && projectDescription != null && pbSavingProject != null){
                btnSaveTask.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!isAllFieldFilled()){
                                    Toast.makeText(requireContext(),"All fields must be filled",Toast.LENGTH_LONG).show();
                                    return;
                                }
                                savingProject();
                                name = projectName.getText().toString();
                                description = projectDescription.getText().toString();
                                saveProject(name,description);
                            }
                        }
                );
            }

            dialog.show();
        }
    }

    private boolean isAllFieldFilled() {
        return !projectName.getText().toString().isEmpty() && !projectDescription.getText().toString().isEmpty();
    }

}