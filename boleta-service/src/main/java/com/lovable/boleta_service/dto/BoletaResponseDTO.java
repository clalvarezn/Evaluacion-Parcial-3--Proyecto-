package com.lovable.boleta_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoletaResponseDTO {

    private Integer idBoleta;
    private Integer idPago;
    private LocalDateTime fechaEmisionBoleta;
    private String metodoPago;
    private String pdfUrl;
    private Integer neto;
    private Integer iva;
    private Integer total;

}
