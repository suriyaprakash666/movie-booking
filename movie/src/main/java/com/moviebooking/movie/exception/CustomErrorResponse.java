package com.moviebooking.movie.exception;

import lombok.Data;

@Data
public class CustomErrorResponse {
    private String timestamp;
    private int status;
    private String error;
    private String message;

    public CustomErrorResponse(String timestamp, int status, String error, String message) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
    }
}
