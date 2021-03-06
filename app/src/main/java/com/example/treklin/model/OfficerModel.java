package com.example.treklin.model;

public class OfficerModel {
    String id, nama, email, password, kendaraan, peralatan, latitude, longitude, created_at, updated_at;
    Float jarak;

    public OfficerModel(String id, String nama, String email, String password, String kendaraan, String peralatan, String latitude, String longitude, String created_at, String updated_at, Float jarak) {
        this.id = id;
        this.nama = nama;
        this.email = email;
        this.password = password;
        this.kendaraan = kendaraan;
        this.peralatan = peralatan;
        this.latitude = latitude;
        this.longitude = longitude;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.jarak = jarak;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKendaraan() {
        return kendaraan;
    }

    public void setKendaraan(String kendaraan) {
        this.kendaraan = kendaraan;
    }

    public String getPeralatan() {
        return peralatan;
    }

    public void setPeralatan(String peralatan) {
        this.peralatan = peralatan;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
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

    public Float getJarak() {
        return jarak;
    }

    public void setJarak(Float jarak) {
        this.jarak = jarak;
    }
}
