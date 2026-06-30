package com.lovable.pedido_service.client.dto;

import lombok.Data;

@Data
public class ProductoResponse {
    private Integer idProducto;
    private String nombreProducto;
    private Double precio;
    private Integer stockActual;
    private Boolean estadoProducto;

}
