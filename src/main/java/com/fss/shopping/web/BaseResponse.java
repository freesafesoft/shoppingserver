package com.fss.shopping.web;

public class BaseResponse {
    private int status = OK_RESULT;
    private String message = "OK";
    private String error;
    private Object result;

    public static final int OK_RESULT = 200;

    public BaseResponse() {
    }

    public BaseResponse(Object result) {
        this.result = result;
    }

    public BaseResponse(int status, String error) {
        this.status = status;
        this.error = error;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
