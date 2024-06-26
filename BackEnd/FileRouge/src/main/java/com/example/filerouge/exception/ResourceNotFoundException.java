package com.example.filerouge.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends Exception{

    private final String field;

    public ResourceNotFoundException(String field, String message) {
        super(message);
        this.field = field;
    }
    public ResourceNotFoundException(String field) {
        this.field = field;
    }
}
