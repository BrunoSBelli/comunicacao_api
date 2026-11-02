package com.luizalebs.comunicacao_api.infraestructure.exceptions;

// "A regra do sistema não permite" -> 409
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
