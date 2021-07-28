package com.example.myapplication.Models;

public class ResponseModel {
    private String message;
    private Boolean status;

    public ResponseModel(String message, Boolean status) {
        this.message = message;
        this.status = status;
    }

    public ResponseModel() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
