package com.lovable.pago_service.model;


import com.lovable.pago_service.enums.EstadoPago;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "pago")
@Data
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_pago")
    private Integer idPago;

    @NotNull
    @Column(name="id_pedido")
    private Integer idPedido;

    @NotNull
    @Positive
    @Column(name="monto")
    private Double monto;

    @Column(name = "fecha_pago")
    private LocalDateTime fechaPago;

    @Enumerated(EnumType.STRING)
    @Column(name="estado_pago")
    private EstadoPago estadoPago;


}
