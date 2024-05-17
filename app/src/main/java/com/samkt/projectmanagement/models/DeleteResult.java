package com.samkt.projectmanagement.models;

public class DeleteResult {
    private final String successMessage;
    private final String errorMessage;

    public DeleteResult(String successMessage, String errorMessage) {
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
