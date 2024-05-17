package com.samkt.projectmanagement.data.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignInResponse {
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private SignInData data;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public SignInData getData() {
        return data;
    }
}

