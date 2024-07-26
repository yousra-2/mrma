package com.mrm.app.models;

public final class FaultMessage {

    private final String status;
    private final String code;
    private final String message;

    public static FaultMessage from(int status, String code, String message){
        return new FaultMessage(String.valueOf(status), code, message);
    }

    FaultMessage(String status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
