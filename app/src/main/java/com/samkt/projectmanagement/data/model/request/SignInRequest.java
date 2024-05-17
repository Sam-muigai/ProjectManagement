package com.samkt.projectmanagement.data.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignInRequest {
    @SerializedName("username_or_email")
    @Expose
    private String usernameOrEmail;
    @SerializedName("password")
    @Expose
    private String password;

    public SignInRequest(String usernameOrEmail, String password) {
        this.usernameOrEmail = usernameOrEmail;
        this.password = password;
    }
}
