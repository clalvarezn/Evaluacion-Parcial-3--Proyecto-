package com.lovable.producto_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponseDTO {
    private Integer idProducto; // El cliente necesita saber el ID de salida
    private String nombreProducto;
    private String descripcion;
    private String talla;
    private String color;
    private Integer precio;
    private String imagen;
    private Boolean estadoProducto;
    private Integer stockActual;
}