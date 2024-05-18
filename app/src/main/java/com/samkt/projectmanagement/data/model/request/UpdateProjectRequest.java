package com.samkt.projectmanagement.data.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateProjectRequest {
    @SerializedName("name")
    @Expose
    private String taskName;
    @SerializedName("deadline")
    @Expose
    private String deadline;

    public UpdateProjectRequest(String taskName, String deadline) {
        this.taskName = taskName;
        this.deadline = deadline;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getDeadline() {
        return deadline;
    }
}
