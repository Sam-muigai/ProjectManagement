package com.samkt.projectmanagement.ui;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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
import com.samkt.projectmanagement.data.model.response.Task;
import com.samkt.projectmanagement.data.preferences.ProjectPreferences;
import com.samkt.projectmanagement.data.repository.TaskRepository;
import com.samkt.projectmanagement.databinding.FragmentTaskBinding;
import com.samkt.projectmanagement.ui.adapters.TaskAdapter;
import com.samkt.projectmanagement.ui.adapters.listeners.TaskListener;
import com.samkt.projectmanagement.ui.viewModels.TaskViewModel;
import com.samkt.projectmanagement.ui.viewModels.factory.TaskViewModelFactory;

import java.util.List;


public class TaskFragment extends Fragment implements TaskListener {

    private FragmentTaskBinding binding;
    private String projectId, projectName, taskName, taskDeadline;
    private BottomSheetDialog dialog;
    private EditText etTaskName, etTaskDeadline;
    private ProgressBar pbSavingTask;
    private Button btnSaveTask;
    private TaskViewModel taskViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            projectName = getArguments().getString("name");
            projectId = getArguments().getString("id");
        }
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        ProjectPreferences projectPreferences = new ProjectPreferences(preferences);
        ApiServiceInstance apiServiceInstance = new ApiServiceInstance(projectPreferences);

        TaskViewModelFactory taskViewModelFactory = new TaskViewModelFactory(new TaskRepository(apiServiceInstance.getApiServices(true)));
        taskViewModel = new ViewModelProvider(this,taskViewModelFactory).get(TaskViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTaskBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String title = "TASKS FOR " + projectName + " PROJECT";
        binding.tvTitle.setText(title);
        getTasks();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        binding.rvTasks.setLayoutManager(linearLayoutManager);

        binding.fabAdd.setOnClickListener(
                new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showAddProjectDialog();
                            }
                        }
        );
    }

    private void showAddProjectDialog() {
        dialog = new BottomSheetDialog(requireContext());
        if (!dialog.isShowing()) {
            dialog.setContentView(R.layout.add_task_dialog);
            btnSaveTask = dialog.findViewById(R.id.btnAddTask);
            etTaskName = dialog.findViewById(R.id.etTaskName);
            pbSavingTask = dialog.findViewById(R.id.pbSavingTask);
            etTaskDeadline = dialog.findViewById(R.id.etDeadlin);

            if (btnSaveTask != null && etTaskName != null && etTaskDeadline != null && pbSavingTask != null) {
                btnSaveTask.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!isAllFieldFilled()) {
                                    Toast.makeText(requireContext(), "All fields must be filled", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                savingTask();
                                taskName = etTaskName.getText().toString();
                                taskDeadline = etTaskDeadline.getText().toString();
                                saveTask(taskName, taskDeadline);
                            }
                        }
                );
            }
            dialog.show();
        }
    }

    private void savingTask() {
        btnSaveTask.setVisibility(View.GONE);
        pbSavingTask.setVisibility(View.VISIBLE);
    }

    private void restoreHomeUiState() {
        btnSaveTask.setVisibility(View.VISIBLE);
        pbSavingTask.setVisibility(View.GONE);
    }

    private void saveTask(String taskName, String taskDeadline) {
        taskViewModel.saveTask(projectId, taskName, taskDeadline).observe(getViewLifecycleOwner(), postResult -> {
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
                getTasks();
            }
        });
    }

    private void getTasks() {
        taskViewModel.getTask(projectId).observe(getViewLifecycleOwner(),allTasks ->{
            if (allTasks.getErrorMessage() != null){
                Toast.makeText(requireContext(),allTasks.getErrorMessage(),Toast.LENGTH_SHORT).show();
                return;
            }

            if (allTasks.getTasks() != null){
                Toast.makeText(requireContext(),"Task Retrieved",Toast.LENGTH_SHORT).show();
                displayTasks(allTasks.getTasks());
            }
        });
    }

    void displayTasks(
            List<Task> tasks
    ){
        TaskAdapter taskAdapter = new TaskAdapter(tasks,this);
        binding.rvTasks.setAdapter(taskAdapter);
    }

    private boolean isAllFieldFilled() {
        return !etTaskName.getText().toString().isEmpty() && !etTaskDeadline.getText().toString().isEmpty();
    }

    @Override
    public void onDeleteClicked(String id) {
        taskViewModel.deleteTask(id).observe(getViewLifecycleOwner(),deleteResult -> {
            if (deleteResult.getErrorMessage() != null){
                Toast.makeText(requireContext(),deleteResult.getErrorMessage(),Toast.LENGTH_SHORT).show();
                return;
            }
            if (deleteResult.getSuccessMessage() != null){
                Toast.makeText(requireContext(),deleteResult.getSuccessMessage(),Toast.LENGTH_SHORT).show();
                getTasks();
            }
        });
    }
}