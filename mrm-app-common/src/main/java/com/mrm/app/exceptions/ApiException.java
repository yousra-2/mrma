package com.mrm.app.exceptions;

public class ApiException extends RuntimeException {

    private final int status;
    private final String code;
    private final String errorMessage;

    public ApiException(int status, String code, String errorMessage){
        this.status = status;
        this.code = code;
        this.errorMessage = errorMessage;
    }

    public int getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

