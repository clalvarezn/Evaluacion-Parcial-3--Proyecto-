package com.lovable.pedido_service.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Pedido no encontrado
    @ExceptionHandler(PedidoNotFoundException.class)
    public ResponseEntity<String> handlePedidoNotFound(PedidoNotFoundException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    // Detalle no encontrado
    @ExceptionHandler(DetalleNotFoundException.class)
    public ResponseEntity<String> handleDetalleNotFound(DetalleNotFoundException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    // Errores de negocio (estado inválido)
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleIllegalState(IllegalStateException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    // Errores de validación
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    // Error general (seguridad)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneral(Exception ex) {
        return ResponseEntity.status(500).body("Error interno del servidor");
    }

}
