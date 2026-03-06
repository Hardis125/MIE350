package com.example.cms.controller.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource, Long id) {
        super("Could not find " + resource + " with ID: " + id);
    }
}