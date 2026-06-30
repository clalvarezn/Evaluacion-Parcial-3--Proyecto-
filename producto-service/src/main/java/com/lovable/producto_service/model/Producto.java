package com.lovable.producto_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="producto")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Producto {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idProducto;

    @Column(nullable = false)
    private String nombreProducto;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private String talla;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private Integer precio;

    @Column(nullable = false)
    private String imagen;

    @Column(nullable = false)
    private Boolean estadoProducto;

    @Column(nullable = false)
    private Integer stockActual;

}
