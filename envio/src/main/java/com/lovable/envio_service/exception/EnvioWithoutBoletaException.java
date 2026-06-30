package com.lovable.envio_service.exception;

public class EnvioWithoutBoletaException extends RuntimeException {
    public EnvioWithoutBoletaException(String message) {
        super(message);
    }
}
