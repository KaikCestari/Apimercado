package com.kaikdev.Tera.exception;

import org.springframework.http.HttpStatus;

public class AdminException extends ApiException{
    public AdminException(HttpStatus status, String message) {
        super(status, message);
    }
}
