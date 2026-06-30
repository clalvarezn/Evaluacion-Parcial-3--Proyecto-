package com.lovable.boleta_service.dto;

import com.lovable.boleta_service.model.EstadoPago;
import com.lovable.boleta_service.model.MetodoPago;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PagoDTO {
    private Integer idPago;
    private Integer idPedido;
    private LocalDateTime fechaPago; //Necesito este dato
    private Double monto; // NEcesito este dato
    private MetodoPago metodoPago; //esto igual
    private EstadoPago estadoPago; //Esto solo me sirve si el estado está en Aprobado
}