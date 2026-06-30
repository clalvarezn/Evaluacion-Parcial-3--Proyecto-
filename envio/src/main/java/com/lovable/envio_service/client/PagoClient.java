package com.lovable.envio_service.client;

import com.lovable.envio_service.Dtos.PagoResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class PagoClient {

    private final WebClient webClient;

    public PagoClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Boolean validarPago(Long idPago) {
        return webClient.get()
                .uri("http://localhost:8083/api/v1/pagos/{id}", idPago)
                .retrieve()
                .bodyToMono(PagoResponse.class)
                .map(pago -> "APROBADO".equalsIgnoreCase(pago.getEstadoPago()))
                .block();
    }
}
