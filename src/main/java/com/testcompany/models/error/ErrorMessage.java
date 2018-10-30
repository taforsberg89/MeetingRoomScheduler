package com.testcompany.models.error;

public class ErrorMessage {

    String errorMessage;

    public ErrorMessage(String msg) {
        errorMessage = msg;

    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
