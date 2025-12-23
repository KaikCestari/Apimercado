package com.kaikdev.Tera.exception;

import org.springframework.http.HttpStatus;

public record AdminErrorResponse( HttpStatus status,
                                  String message) {

}
