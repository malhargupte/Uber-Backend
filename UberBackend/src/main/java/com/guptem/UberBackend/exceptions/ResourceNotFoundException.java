package com.guptem.UberBackend.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {

    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

}
