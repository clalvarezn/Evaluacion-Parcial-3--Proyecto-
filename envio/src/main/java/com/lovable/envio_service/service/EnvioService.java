package com.lovable.envio_service.service;


import com.lovable.envio_service.client.BoletaClient;
import com.lovable.envio_service.client.PagoClient;
import com.lovable.envio_service.exception.EnvioNotFoundException;
import com.lovable.envio_service.exception.EnvioWithoutBoletaException;
import com.lovable.envio_service.model.Envio;
import com.lovable.envio_service.model.EstadoEnvio;
import com.lovable.envio_service.repository.EnvioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class EnvioService {

    private final EnvioRepository envioRepository;
    private final PagoClient pagoClient;
    private final BoletaClient boletaClient;

    public EnvioService(EnvioRepository envioRepository, PagoClient pagoClient, BoletaClient boletaClient) {
        this.envioRepository = envioRepository;
        this.pagoClient = pagoClient;
        this.boletaClient = boletaClient;
    }

    // Crear Envío validando Pago y Boleta
    public Envio crearEnvio(Envio envio) {
        boolean pagoValido = pagoClient.validarPago(envio.getIdPago());
        boolean boletaValida = boletaClient.validarBoleta(envio.getIdBoleta());

        if (!pagoValido || !boletaValida) {
            throw new EnvioWithoutBoletaException("No se puede generar envío sin pago aprobado y boleta emitida");
        }

        envio.setEstadoEnvio(EstadoEnvio.PENDIENTE);
        envio.setFechaDespacho(LocalDate.now());
        return envioRepository.save(envio);
    }

    // Obtener Envío por ID
    public Envio obtenerEnvio(Long idEnvio) {
        return envioRepository.findById(idEnvio)
                .orElseThrow(() -> new EnvioNotFoundException("Envio con id " + idEnvio + " no encontrado"));
    }

    // Listar todos los envíos
    public List<Envio> listarEnvios() {
        return envioRepository.findAll();
    }

    // Actualizar estado o fecha de entrega
    public Envio actualizarEnvio(Long idEnvio, Envio envioActualizado) {
        Envio envio = obtenerEnvio(idEnvio);
        envio.setEstadoEnvio(envioActualizado.getEstadoEnvio());
        envio.setFechaEntrega(envioActualizado.getFechaEntrega());
        return envioRepository.save(envio);
    }

    // Eliminar Envío
    public void eliminarEnvio(Long idEnvio) {
        envioRepository.deleteById(idEnvio);
    }
}



