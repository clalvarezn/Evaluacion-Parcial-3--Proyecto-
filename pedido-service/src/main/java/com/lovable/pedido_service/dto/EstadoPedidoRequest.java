package com.lovable.pedido_service.dto;

import com.lovable.pedido_service.enums.EstadoPedido;
import lombok.Data;

@Data
public class EstadoPedidoRequest {
    private EstadoPedido nuevoEstado;
}
