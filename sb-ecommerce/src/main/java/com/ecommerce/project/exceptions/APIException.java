package com.ecommerce.project.exceptions;

public class APIException extends RuntimeException{
    private static final long serialVersionUUID = 1l;

    public APIException() {
    }

    public APIException(String message) {
        super(message);
    }
}
