package com.lovable.boleta_service.service;

import com.lovable.boleta_service.dto.BoletaRequestDTO;
import com.lovable.boleta_service.dto.BoletaResponseDTO;
import com.lovable.boleta_service.model.Boleta;
import com.lovable.boleta_service.model.EstadoPago;
import com.lovable.boleta_service.model.MetodoPago;
import com.lovable.boleta_service.repository.BoletaRepository;
import com.lovable.boleta_service.dto.PagoDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BoletaServiceImpl implements IBoletaService {

    private final BoletaRepository boletaRepository;
    private final WebClient.Builder webClientBuilder;

    // 1. OBTENER TODOS
    @Override
    public List<BoletaResponseDTO> obtenerTodos() {
        return boletaRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // 2. OBTENER POR ID
    @Override
    public BoletaResponseDTO obtenerPorId(Integer idBoleta) {
        log.info("==> PETICIÓN: Buscando boleta con ID: {}", idBoleta);

        Boleta boleta = boletaRepository.findById(idBoleta)
                .orElseThrow(() -> new RuntimeException("Boleta no encontrada con el ID: " + idBoleta));
        return convertirADTO(boleta);
    }

    // 3. Crear, acá se manda una consulta al pago, se verifica que esté Aprobado y se calcula el IVA

    @Override
    public BoletaResponseDTO crear(BoletaRequestDTO request) {
        log.info("==> PETICIÓN BOLETA: Solicitando datos de pago: {}", request.getIdPago());

        PagoDTO pagoResponse;
        try {
            pagoResponse = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8083/api/v1/pagos/" + request.getIdPago())
                    .retrieve()
                    .onStatus(
                            status -> status.isError(),
                            response -> {
                                log.error("Error al consultar pago-service");
                                throw new RuntimeException("Error al obtener datos del pago");
                            }
                    )
                    .bodyToMono(PagoDTO.class)
                    .block();

        } catch (Exception e) {
            log.error("ERROR DE COMUNICACIÓN: No se pudo conectar con el servicio de pagos. Motivo: {}", e.getMessage());
            throw new RuntimeException("Error al validar el pago: El microservicio de pagos no responde.");
        }

        if (pagoResponse == null) {
            throw new RuntimeException("El pago con ID " + request.getIdPago() + " no existe.");
        }

        // Validación con ENUM
        if (pagoResponse.getEstadoPago() != EstadoPago.APROBADO) {
            log.error("ERROR BOLETA: El pago ID {} está {}, bloqueo de emisión.",
                    request.getIdPago(), pagoResponse.getEstadoPago());
            throw new RuntimeException("No se puede generar la boleta: El pago no se encuentra APROBADO.");
        }

        // Acá va el cálculo del IVA y el neto, el cálculo es el total pago dividido por 1.19 y la diferencia es el IVA
        // los montos están redondeados
        int montoTotalReal = pagoResponse.getMonto().intValue();
        int netoCalculado = (int) Math.round(montoTotalReal / 1.19);
        int ivaCalculado = montoTotalReal - netoCalculado;

        // Guardado en Base de Datos
        Boleta boleta = new Boleta();
        boleta.setIdPago(pagoResponse.getIdPago());
        boleta.setFechaEmisionBoleta(pagoResponse.getFechaPago());
        boleta.setMetodoPago(MetodoPago.TRANSFERENCIA);
        boleta.setNeto(netoCalculado);
        boleta.setIva(ivaCalculado);
        boleta.setTotal(montoTotalReal);
        boleta.setPdfUrl("https://LovableEcomerce/facturacion/boletas/pdf/folio_" + boleta.getIdPago() + ".pdf");

        Boleta boletaGuardada = boletaRepository.save(boleta);
        return convertirADTO(boletaGuardada);
    }

    // Mapeador manual adaptado a Boleta
    private BoletaResponseDTO convertirADTO(Boleta boleta) {
        BoletaResponseDTO dto = new BoletaResponseDTO();
        dto.setIdBoleta(boleta.getIdBoleta());
        dto.setIdPago(boleta.getIdPago());
        dto.setFechaEmisionBoleta(boleta.getFechaEmisionBoleta());
        dto.setMetodoPago(MetodoPago.TRANSFERENCIA.name()); //se fuerza a que siempres ea transferencia por el momento
        dto.setNeto(boleta.getNeto());
        dto.setIva(boleta.getIva());
        dto.setTotal(boleta.getTotal());
        dto.setPdfUrl(boleta.getPdfUrl());
        return dto;
    }
}