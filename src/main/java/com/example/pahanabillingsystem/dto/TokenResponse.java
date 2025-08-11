package com.example.pahanabillingsystem.dto;

public class TokenResponse {

    private String token;

    public TokenResponse(String token) {
        this.token = token;
    }

    // Getter and Setter methods
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
