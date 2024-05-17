package com.samkt.projectmanagement.data.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllProjectsResponse {
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Project> projects;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public List<Project> getProjects() {
        return projects;
    }
}

