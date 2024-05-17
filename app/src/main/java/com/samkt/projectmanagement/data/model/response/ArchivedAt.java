package com.samkt.projectmanagement.data.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ArchivedAt {
    @SerializedName("Time")
    @Expose
    private String time;
    @SerializedName("Valid")
    @Expose
    private Boolean valid;

    public ArchivedAt(String time, Boolean valid) {
        this.time = time;
        this.valid = valid;
    }
}
