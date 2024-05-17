package com.samkt.projectmanagement.models;

import com.samkt.projectmanagement.data.model.response.Project;

import java.util.List;

public class AllProjects {
    private String errorMessage;
    private List<Project> projects;

    public AllProjects(String errorMessage, List<Project> projects) {
        this.errorMessage = errorMessage;
        this.projects = projects;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
}
