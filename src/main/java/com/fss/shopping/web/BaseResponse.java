package com.fss.shopping.web;

public class BaseResponse {
    private String status;
    private String error;
    private Object result;

    public BaseResponse(final String status) {
        this.status = status;
    }

    public BaseResponse(final String status, final String error) {
        this.status = status;
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(final String error) {
        this.error = error;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(final Object result) {
        this.result = result;
    }

}
