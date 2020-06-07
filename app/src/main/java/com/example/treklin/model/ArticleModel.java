package com.example.treklin.model;

public class ArticleModel {
    private String id, judul, penulis, foto, isi, created_at, updated_at;

    public ArticleModel(String id, String judul, String penulis, String foto, String isi, String created_at, String updated_at) {
        this.id = id;
        this.judul = judul;
        this.penulis = penulis;
        this.foto = foto;
        this.isi = isi;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
