package com.samkt.projectmanagement.ui.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.samkt.projectmanagement.data.model.request.CreateProjectRequest;
import com.samkt.projectmanagement.data.repository.ProjectRepository;
import com.samkt.projectmanagement.models.AllProjects;
import com.samkt.projectmanagement.models.DeleteResult;
import com.samkt.projectmanagement.models.PostResult;
import com.samkt.projectmanagement.models.UpdateResult;

public class HomeViewModel extends ViewModel {

    private final ProjectRepository repository;
    public HomeViewModel(ProjectRepository repository){
        this.repository = repository;
    }

    public LiveData<PostResult> saveProject(
            String projectName,
            String projectDescription
    ){
        CreateProjectRequest createProjectRequest = new CreateProjectRequest(projectName,projectDescription);
        return repository.saveProject(createProjectRequest);
    }

    public LiveData<DeleteResult> deleteProject(
            String id
    ){
        return repository.deleteProject(id);
    }

    public LiveData<UpdateResult> updateProject(
        String id,
        String name,
        String description
    ){
        CreateProjectRequest createProjectRequest = new CreateProjectRequest(name,description);
        return repository.updateProject(id,createProjectRequest);
    }

    public LiveData<AllProjects> getProjects(){
        return repository.getAllProject();
    }


}
