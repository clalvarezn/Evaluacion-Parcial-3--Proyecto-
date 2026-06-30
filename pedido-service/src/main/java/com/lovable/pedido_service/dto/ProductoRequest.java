package com.lovable.pedido_service.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoRequest {

    private Integer idProducto;
    private Integer cantidadProducto;
    private Double precioProducto;


}
