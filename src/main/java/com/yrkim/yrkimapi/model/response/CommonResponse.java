package com.yrkim.yrkimapi.model.response;

public enum CommonResponse {

    SUCCESS(200 , "success");

    int code;
    String message;

    CommonResponse(int code , String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
