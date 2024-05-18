package com.samkt.projectmanagement.data.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Task {
    @SerializedName("Uuid")
    @Expose
    private String uuid;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Deadline")
    @Expose
    private String deadline;
    @SerializedName("CreatedAt")
    @Expose
    private String createdAt;
    @SerializedName("ProjectUuid")
    @Expose
    private String projectUuid;
    @SerializedName("ProjectName")
    @Expose
    private String projectName;
    @SerializedName("ArchivedAt")
    @Expose
    private ArchivedAt archivedAt;

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getProjectUuid() {
        return projectUuid;
    }

    public String getProjectName() {
        return projectName;
    }

    public ArchivedAt getArchivedAt() {
        return archivedAt;
    }
}

