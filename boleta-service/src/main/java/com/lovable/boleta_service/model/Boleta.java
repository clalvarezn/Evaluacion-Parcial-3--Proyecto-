package com.lovable.boleta_service.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name="boleta")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Boleta {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idBoleta;

    @Column(nullable = false)
    private Integer idPago;

    @Column(nullable = false)
    private LocalDateTime fechaEmisionBoleta;

    @Enumerated(EnumType.STRING) // Para obtener el ENUM
    private MetodoPago metodoPago;

    @Column(nullable = false)
    private String pdfUrl;

    @Column(nullable = false)
    private Integer neto;

    @Column(nullable = false)
    private Integer iva;

    @Column(nullable = false)
    private Integer total;

}
