package com.yrkim.yrkimapi.exception;

public class BaseException extends Exception {

    public BaseException(String message , Throwable throwable) {
        super(message , throwable);
    }

    public BaseException(String message) {
        super(message);
    }
}
