package com.example.treklin.model;

import java.util.List;

public class ResponseModelArticle {
    private String status, message;
    private List<ArticleModel> data;
    private ArticleModel article;

    public ResponseModelArticle(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResponseModelArticle(String status, String message, List<ArticleModel> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ResponseModelArticle(String status, String message, List<ArticleModel> data, ArticleModel article) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.article = article;
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

    public ArticleModel getArticle() {
        return article;
    }

    public void setArticle(ArticleModel article) {
        this.article = article;
    }
}
