package com.example.treklin.model;

import java.util.List;

public class ResponseModel {

    private String status, message;
    private UserModel dataUser;

    public ResponseModel(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResponseModel(String status, String message, UserModel dataUser) {
        this.status = status;
        this.message = message;
        this.dataUser = dataUser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserModel getDataUser() {
        return dataUser;
    }

    public void setDataUser(UserModel dataUser) {
        this.dataUser = dataUser;
    }
}
