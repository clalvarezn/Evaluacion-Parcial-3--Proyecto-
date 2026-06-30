package com.lovable.envio_service.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "envios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEnvio;

    @NotBlank(message = "Debe Ingresar una direccion")
    @Column(nullable = false)
    private String direccionEnvio;

    @NotBlank(message = "Debe Ingresar una comuna ")
    @Column(nullable = false)
    private String comuna;

    @NotNull(message = "Debe Ingresar un costo")
    @Positive(message = "El costo de envío debe ser mayor a 0")
    @Column(nullable = false)
    private Double costoEnvio;

    @NotNull(message = "Debe Ingreasar un estado del envio (PENDIENTE,EN_CAMINO,ENTREGADO,CANCELADO)")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoEnvio estadoEnvio;

    @NotNull(message = "Debe ingresar una fecha de despacho")
    @Column(nullable = false)
    private LocalDate fechaDespacho;

    private LocalDate fechaEntrega;

    @NotNull(message = "Debe ingresar el id de la boleta")
    @Column(nullable = false)
    private Long idBoleta;

    @NotNull(message = "Debe ingresar el id del pago")
    @Column(nullable = false)
    private Long idPago;


}
