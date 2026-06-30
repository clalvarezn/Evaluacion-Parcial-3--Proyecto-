package com.lovable.envio_service.Dtos;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PagoResponse {

    private Long idPago;
    private Long idPedido;
    private String estadoPago; // "APROBADO", "RECHAZADO"
    private Double monto;
    private LocalDateTime fechaPago;


}
