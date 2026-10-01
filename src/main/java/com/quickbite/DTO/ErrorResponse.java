package com.quickbite.DTO;

public class ErrorResponse {

    private int status;
    private Object message;

    public ErrorResponse(int status, Object message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public Object getMessage() {
        return message;
    }
}