package com.fss.shopping.web;

import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.stream.Collectors;

public class BaseResponse {
    private String message;
    private String error;

    public BaseResponse(final String message) {
        this.message = message;
    }

    public BaseResponse(final String message, final String error) {
        this.message = message;
        this.error = error;
    }

    public BaseResponse(List<ObjectError> allErrors, String error) {
        this.error = error;
        this.message = allErrors.stream()
                .map(e -> e.getDefaultMessage())
                .collect(Collectors.joining(","));
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(final String error) {
        this.error = error;
    }

}
