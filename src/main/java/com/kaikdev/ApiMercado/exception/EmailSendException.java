package com.kaikdev.ApiMercado.exception;

import org.springframework.http.HttpStatus;

public class EmailSendException extends ApiException {

    public EmailSendException(String message, Throwable cause) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
        initCause(cause);
    }
}
