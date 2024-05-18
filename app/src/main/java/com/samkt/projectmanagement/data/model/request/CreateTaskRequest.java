package com.samkt.projectmanagement.data.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateTaskRequest {
    @SerializedName("project_uuid")
    @Expose
    private String projectUuid;
    @SerializedName("name")
    @Expose
    private String taskName;
    @SerializedName("deadline")
    @Expose
    private String deadline;

    public CreateTaskRequest(String projectUuid, String taskName, String deadline) {
        this.projectUuid = projectUuid;
        this.taskName = taskName;
        this.deadline = deadline;
    }

    public String getProjectUuid() {
        return projectUuid;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getDeadline() {
        return deadline;
    }
}