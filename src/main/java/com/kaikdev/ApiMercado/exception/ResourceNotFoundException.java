package com.kaikdev.ApiMercado.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends ApiException {

    public ResourceNotFoundException(String resourceName, Object identifier) {
        super(HttpStatus.NOT_FOUND, "%s não encontrado(a) para o identificador %s"
                .formatted(resourceName, identifier));
    }

    public ResourceNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
