package com.lovable.envio_service.client;

import com.lovable.envio_service.Dtos.BoletaResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class BoletaClient {

    private  final WebClient webClient;

    public BoletaClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Boolean validarBoleta(Long idBoleta) {
        return webClient.get()
                .uri("http://localhost:8084/api/v1/boletas/{id}", idBoleta)
                .retrieve()
                .bodyToMono(BoletaResponse.class)
                .map(boleta -> boleta != null)
                .block();
    }
}
