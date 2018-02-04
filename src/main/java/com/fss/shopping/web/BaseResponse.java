package com.fss.shopping.web;

import java.io.Serializable;

public class BaseResponse implements Serializable {
    private String status;
    private String error;

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

}
