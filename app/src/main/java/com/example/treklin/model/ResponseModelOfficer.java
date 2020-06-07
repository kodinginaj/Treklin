package com.example.treklin.model;

import java.util.List;

public class ResponseModelOfficer {

    String status,message;
    List<OfficerModel> officer;

    public ResponseModelOfficer(String status, String message, List<OfficerModel> officer) {
        this.status = status;
        this.message = message;
        this.officer = officer;
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

    public List<OfficerModel> getOfficer() {
        return officer;
    }

    public void setOfficer(List<OfficerModel> officer) {
        this.officer = officer;
    }
}
