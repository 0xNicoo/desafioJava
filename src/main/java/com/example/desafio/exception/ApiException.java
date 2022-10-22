package com.example.desafio.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ApiException {
    private String massage;
    private Throwable throwable;
    private HttpStatus httpStatus;
    private ZonedDateTime timestamp;

    public ApiException(String massage, HttpStatus httpStatus, ZonedDateTime timestamp) {
        this.massage = massage;

        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }


    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
