package com.lovable.envio_service.Dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoletaResponse {

    private Long idBoleta;
    private Long idPago;
    private LocalDateTime fechaEmisionBoleta;
}
