package com.lovable.pedido_service.client;

import com.lovable.pedido_service.client.dto.ProductoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ProductoClient {


    private final WebClient webClient = WebClient.builder().build();

    // Informacion del servicio producto
    @Value("${microservices.producto.base-url}")
    private String productoBaseUrl;

    //Metodo que obtiene informacion del servicio producto
    public ProductoResponse obtenerProductoPorId(Integer idProducto) {
        return webClient.get()
                .uri(productoBaseUrl + "/api/v1/productos/" + idProducto)
                .retrieve()
                .bodyToMono(ProductoResponse.class)
                .block();
    }


}
