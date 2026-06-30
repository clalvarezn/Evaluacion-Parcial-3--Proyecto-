package com.lovable.boleta_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class BoletaRequestDTO {

    @NotNull(message = "El id es obligatorio")
    private Integer idPago;


}
