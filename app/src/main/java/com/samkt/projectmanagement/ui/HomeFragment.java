package com.samkt.projectmanagement.ui;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;

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
import com.samkt.projectmanagement.data.model.response.Project;
import com.samkt.projectmanagement.data.preferences.ProjectPreferences;
import com.samkt.projectmanagement.data.repository.ProjectRepository;
import com.samkt.projectmanagement.databinding.FragmentHomeBinding;
import com.samkt.projectmanagement.ui.adapters.ProjectAdapter;
import com.samkt.projectmanagement.ui.adapters.listeners.ProjectsListeners;
import com.samkt.projectmanagement.ui.viewModels.HomeViewModel;
import com.samkt.projectmanagement.ui.viewModels.factory.HomeViewModelFactory;

import java.util.List;
import java.util.Timer;

import timber.log.Timber;


public class HomeFragment extends Fragment implements ProjectsListeners {

    private FragmentHomeBinding binding;
    private BottomSheetDialog dialog;
    private HomeViewModel homeViewModel;

    private EditText projectName, projectDescription;
    private ProgressBar pbSavingProject;
    private Button btnSaveProject;
    private String name, description;

    private List<Project> userProjects;

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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        binding.rvProjects.setLayoutManager(linearLayoutManager);

        binding.fabAdd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAddProjectDialog();
                    }
                }
        );

        getProjects();
    }

    private void saveProject(
            String name,
            String description
    ) {
        homeViewModel.saveProject(name, description).observe(getViewLifecycleOwner(), postResult -> {
            if (postResult.getErrorMessage() != null) {
                restoreHomeUiState();
                Toast.makeText(requireContext(), postResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
                return;
            }
            if (postResult.getSuccessMessage() != null) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                Toast.makeText(requireContext(), postResult.getSuccessMessage(), Toast.LENGTH_SHORT).show();
                getProjects();
            }
        });
    }

    private void restoreHomeUiState() {
        btnSaveProject.setVisibility(View.VISIBLE);
        pbSavingProject.setVisibility(View.GONE);
    }

    private void savingProject() {
        btnSaveProject.setVisibility(View.GONE);
        pbSavingProject.setVisibility(View.VISIBLE);
    }

    private void showAddProjectDialog() {
        dialog = new BottomSheetDialog(requireContext());
        if (!dialog.isShowing()) {
            dialog.setContentView(R.layout.add_project_dialog);
            btnSaveProject = dialog.findViewById(R.id.btnAddProject);
            projectName = dialog.findViewById(R.id.etProjectName);
            pbSavingProject = dialog.findViewById(R.id.pbSavingProject);
            projectDescription = dialog.findViewById(R.id.etProjectDescription);

            if (btnSaveProject != null && projectName != null && projectDescription != null && pbSavingProject != null) {
                btnSaveProject.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!isAllFieldFilled()) {
                                    Toast.makeText(requireContext(), "All fields must be filled", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                savingProject();
                                name = projectName.getText().toString();
                                description = projectDescription.getText().toString();
                                saveProject(name, description);
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

    private void getProjects() {
        homeViewModel.getProjects().observe(
                getViewLifecycleOwner(), info -> {
                    if (info.getErrorMessage() != null) {
                        showError(info.getErrorMessage());
                        return;
                    }
                    if (info.getProjects() != null) {
                        displayProjects(info.getProjects());
                    }
                }
        );
    }

    private void displayProjects(List<Project> projects) {
        userProjects = projects;
        ProjectAdapter projectAdapter = new ProjectAdapter(projects, this);
        binding.rvProjects.setAdapter(projectAdapter);
        binding.rvProjects.setVisibility(View.VISIBLE);
    }

    private void showError(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEditClicked(String id, String name, String description) {
        showEditProjectDialog(id, name, description);
    }

    private void showEditProjectDialog(String id, String name, String description) {
        dialog = new BottomSheetDialog(requireContext());
        if (!dialog.isShowing()) {
            dialog.setContentView(R.layout.add_project_dialog);
            btnSaveProject = dialog.findViewById(R.id.btnAddProject);
            projectName = dialog.findViewById(R.id.etProjectName);
            pbSavingProject = dialog.findViewById(R.id.pbSavingProject);
            projectDescription = dialog.findViewById(R.id.etProjectDescription);

            if (btnSaveProject != null && projectName != null && projectDescription != null && pbSavingProject != null) {
                projectName.setText(name);
                projectDescription.setText(description);
                btnSaveProject.setText("UPDATE PROJECT");
                btnSaveProject.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!isAllFieldFilled()) {
                                    Toast.makeText(requireContext(), "All fields must be filled", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                savingProject();
                                String pjctName = projectName.getText().toString();
                                String pjctDescription = projectDescription.getText().toString();
                                updateProject(id, pjctName, pjctDescription);
                            }
                        }
                );
            }
            dialog.show();
        }
    }

    private void updateProject(String id, String name, String description) {
        homeViewModel.updateProject(id, name, description).observe(getViewLifecycleOwner(), postResult -> {
            if (postResult.getErrorMessage() != null) {
                restoreHomeUiState();
                Toast.makeText(requireContext(), postResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
                return;
            }
            if (postResult.getSuccessMessage() != null) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                Toast.makeText(requireContext(), postResult.getSuccessMessage(), Toast.LENGTH_SHORT).show();
                getProjects();
            }
        });
    }

    @Override
    public void onDeleteClicked(String id) {
        homeViewModel.deleteProject(id).observe(getViewLifecycleOwner(), deleteResult -> {
            if (deleteResult.getErrorMessage() != null) {
                showError(deleteResult.getErrorMessage());
                return;
            }
            if (deleteResult.getSuccessMessage() != null) {
                Toast.makeText(requireContext(), deleteResult.getSuccessMessage(), Toast.LENGTH_LONG).show();
                getProjects();

                if (userProjects.size() == 1) {
                    binding.rvProjects.setVisibility(View.GONE);
                }
            }
        });
    }


    @Override
    public void onProjectClicked(String id, String name) {
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putString("name", name);
        NavHostFragment.findNavController(HomeFragment.this).navigate(
                R.id.action_homeFragment_to_taskFragment,
                bundle
        );
    }
}