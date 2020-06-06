package com.example.treklin.model;

import java.util.List;

public class ResponseModel {

    private String status, message;
    private List<ArticleModel> listArticle;

    public ResponseModel(String status, String message) {
        this.status = status;
        this.message = message;
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
}
