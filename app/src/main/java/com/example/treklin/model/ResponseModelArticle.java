package com.example.treklin.model;

import java.util.List;

public class ResponseModelArticle {
    private String status, message;
    private List<ArticleModel> data;

    public ResponseModelArticle(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResponseModelArticle(String status, String message, List<ArticleModel> data) {
        this.status = status;
        this.message = message;
        this.data = data;
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

    public List<ArticleModel> getData() {
        return data;
    }

    public void setData(List<ArticleModel> data) {
        this.data = data;
    }
}
