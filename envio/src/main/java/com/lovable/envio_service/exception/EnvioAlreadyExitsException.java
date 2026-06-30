package com.lovable.envio_service.exception;

public class EnvioAlreadyExitsException extends RuntimeException {
    public EnvioAlreadyExitsException(String message) {
        super(message);
    }
}
