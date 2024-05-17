package com.samkt.projectmanagement.data.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Project{
    @SerializedName("Uuid")
    @Expose
    private String uuid;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("UserUuid")
    @Expose
    private String userUuid;
    @SerializedName("CreatedAt")
    @Expose
    private String createdAt;
    @SerializedName("ArchivedAt")
    @Expose
    private ArchivedAt archivedAt;

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public ArchivedAt getArchivedAt() {
        return archivedAt;
    }
}
