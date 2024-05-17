package com.samkt.projectmanagement.models;

public class PostResult {
    private final String successMessage;
    private final String errorMessage;

    public PostResult(String successMessage, String errorMessage) {
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
