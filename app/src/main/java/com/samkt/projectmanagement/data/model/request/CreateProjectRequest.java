package com.samkt.projectmanagement.data.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateProjectRequest {
    @SerializedName("name")
    @Expose
    private String projectName;
    @SerializedName("description")
    @Expose
    private String description;

    public CreateProjectRequest(String projectName, String description) {
        this.projectName = projectName;
        this.description = description;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getDescription() {
        return description;
    }
}
