package com.lovable.pago_service.client;

import com.lovable.pago_service.client.dto.EstadoPedidoRequest;
import com.lovable.pago_service.client.dto.PedidoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class PedidoClient {

    private final WebClient webClient;

    //Informacion del Servicio Pedido
    @Value("http://localhost:8086")
    private String pedidoBaseUrl;

    public PedidoClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public void cambiarEstadoPedido(Integer idPedido) {
        EstadoPedidoRequest request = new EstadoPedidoRequest();
        request.setNuevoEstado("PAGADO");

        webClient.put()
                .uri(pedidoBaseUrl + "/api/v1/pedidos/" + idPedido + "/estado")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Void.class)
                .block();

    }

    //Obtiene el total del pedido segun idPedido
    public PedidoResponse obtenerPedidoPorId(Integer idPedido) {

        return webClient.get()
                .uri(pedidoBaseUrl + "/api/v1/pedidos/" + idPedido)
                .retrieve()
                .bodyToMono(PedidoResponse.class)
                .block();
    }

}
