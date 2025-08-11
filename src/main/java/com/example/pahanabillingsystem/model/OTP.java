package com.example.pahanabillingsystem.model;

public class OTP {

    private int id;
    private int userId;
    private String email;
    private String code;

    public OTP() {
    }

    public OTP(int id, int userId, String email, String code) {
        this.id = id;
        this.userId = userId;
        this.email = email;
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
