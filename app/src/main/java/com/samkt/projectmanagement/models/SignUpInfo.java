package com.samkt.projectmanagement.models;

public class SignUpInfo {

    private final String successMessage;
    private final String errorMessage;

    public SignUpInfo(String successMessage, String errorMessage) {
        this.successMessage = successMessage;
        this.errorMessage = errorMessage;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
