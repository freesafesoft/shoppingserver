package com.fss.shopping.web;

public class ErrorResponse extends  BaseResponse {

    public ErrorResponse(String error) {
        super("", error);
    }
}
