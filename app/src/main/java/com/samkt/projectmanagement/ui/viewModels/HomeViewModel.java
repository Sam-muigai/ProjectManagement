package com.samkt.projectmanagement.ui.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.samkt.projectmanagement.data.model.request.CreateProjectRequest;
import com.samkt.projectmanagement.data.repository.ProjectRepository;
import com.samkt.projectmanagement.models.AllProjects;
import com.samkt.projectmanagement.models.PostResult;

public class HomeViewModel extends ViewModel {

    private final ProjectRepository repository;
    public HomeViewModel(ProjectRepository repository){
        this.repository = repository;
    }

    public LiveData<PostResult> saveTask(
            String projectName,
            String projectDescription
    ){
        CreateProjectRequest createProjectRequest = new CreateProjectRequest(projectName,projectDescription);
        return repository.saveProject(createProjectRequest);
    }

    public LiveData<AllProjects> getProjects(){
        return repository.getAllProject();
    }
}