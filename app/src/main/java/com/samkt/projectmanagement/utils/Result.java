package com.samkt.projectmanagement.utils;

public abstract class Result<T> {
    private Result() {}

    public static final class Success<T> extends Result<T> {
        public T data;

        public Success(T data) {
            this.data = data;
        }
    }

    public static final class Error<T> extends Result<T> {
        public String errorMessage;

        public Error(String errorMessage) {
            this.errorMessage = errorMessage;
        }
    }
}
