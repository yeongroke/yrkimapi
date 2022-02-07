package com.yrkim.yrkimapi.exception;

public class ParameterValidException extends RuntimeException {
    public ParameterValidException(String msg, Throwable t) {
        super(msg, t);
    }

    public ParameterValidException(String msg) {
        super(msg);
    }

    public ParameterValidException() {
        super();
    }
}
