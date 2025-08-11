package com.example.pahanabillingsystem.dto;

public class ErrorResponse {
    private String error;

    public ErrorResponse(String error) {
        this.error = error;
    }

    // Getter and Setter methods
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
