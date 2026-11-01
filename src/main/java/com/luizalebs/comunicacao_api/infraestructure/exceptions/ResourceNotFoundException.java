package com.luizalebs.comunicacao_api.infraestructure.exceptions;

// "Não achei" -> 404
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
