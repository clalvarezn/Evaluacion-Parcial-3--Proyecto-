package com.lovable.pedido_service.client.dto;

import lombok.Data;

@Data
public class UsuarioResponse {
    private Integer idUsuario;
    private String nombreUsuario;
    private String estadoUsuario;

}
