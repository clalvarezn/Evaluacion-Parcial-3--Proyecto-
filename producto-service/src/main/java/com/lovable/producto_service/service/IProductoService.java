package com.lovable.producto_service.service;

import com.lovable.producto_service.dto.ProductoRequestDTO;
import com.lovable.producto_service.dto.ProductoResponseDTO;
import java.util.List;

public interface IProductoService {
    List<ProductoResponseDTO> obtenerTodos();
    ProductoResponseDTO obtenerPorId(Integer idProducto);
    ProductoResponseDTO crear(ProductoRequestDTO request);
    ProductoResponseDTO actualizar(Integer idProducto, ProductoRequestDTO request);
    void actualizarStock(Integer idProducto, Integer cantidad);
    void desactivarProducto(Integer idProducto);
}