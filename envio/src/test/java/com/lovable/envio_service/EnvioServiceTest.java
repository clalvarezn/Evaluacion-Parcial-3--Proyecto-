package com.lovable.envio_service;


import com.lovable.envio_service.client.BoletaClient;
import com.lovable.envio_service.client.PagoClient;
import com.lovable.envio_service.exception.EnvioNotFoundException;
import com.lovable.envio_service.exception.EnvioWithoutBoletaException;
import com.lovable.envio_service.model.Envio;
import com.lovable.envio_service.model.EstadoEnvio;
import com.lovable.envio_service.repository.EnvioRepository;
import com.lovable.envio_service.service.EnvioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@SpringBootTest
public class EnvioServiceTest {

    @Autowired
    private EnvioService envioService;

    @MockBean
    private EnvioRepository envioRepository;

    @MockBean
    private PagoClient pagoClient;

    @MockBean
    private BoletaClient boletaClient;

    // Test para crear envío válido
    @Test
    void testCrearEnvioValido() {
        Envio envio = new Envio();
        envio.setDireccionEnvio("Av. Siempre Viva 123");
        envio.setComuna("Santiago");
        envio.setCostoEnvio(5000.0);
        envio.setIdPago(1L);
        envio.setIdBoleta(1L);

        when(pagoClient.validarPago(1L)).thenReturn(true);
        when(boletaClient.validarBoleta(1L)).thenReturn(true);
        when(envioRepository.save(any(Envio.class))).thenReturn(envio);

        Envio guardado = envioService.crearEnvio(envio);

        assertNotNull(guardado);
        assertEquals(EstadoEnvio.PENDIENTE, guardado.getEstadoEnvio());
        assertEquals("Santiago", guardado.getComuna());
    }

    // Test para crear envío inválido (boleta no válida)
    @Test
    void testCrearEnvioSinBoletaValida() {
        Envio envio = new Envio();
        envio.setDireccionEnvio("Calle Falsa 456");
        envio.setComuna("Puente Alto");
        envio.setCostoEnvio(3000.0);
        envio.setIdPago(2L);
        envio.setIdBoleta(2L);

        when(pagoClient.validarPago(2L)).thenReturn(true);
        when(boletaClient.validarBoleta(2L)).thenReturn(false);

        assertThrows(EnvioWithoutBoletaException.class, () -> envioService.crearEnvio(envio));
    }

    // Test para listar envíos
    @Test
    void testListarEnvios() {
        Envio envio = new Envio();
        envio.setDireccionEnvio("Av. Los Pinos 789");
        envio.setComuna("La Florida");
        envio.setCostoEnvio(4000.0);
        envio.setEstadoEnvio(EstadoEnvio.PENDIENTE);
        envio.setFechaDespacho(LocalDate.now());
        envio.setIdPago(3L);
        envio.setIdBoleta(3L);

        when(envioRepository.findAll()).thenReturn(List.of(envio));

        List<Envio> envios = envioService.listarEnvios();

        assertNotNull(envios);
        assertEquals(1, envios.size());
        assertEquals("La Florida", envios.get(0).getComuna());
    }

    // Test para obtener envío por ID
    @Test
    void testObtenerEnvioPorId() {
        Envio envio = new Envio();
        envio.setIdEnvio(10L);
        envio.setDireccionEnvio("Av. Central 100");
        envio.setComuna("Ñuñoa");
        envio.setCostoEnvio(6000.0);
        envio.setEstadoEnvio(EstadoEnvio.EN_CAMINO);
        envio.setFechaDespacho(LocalDate.now());
        envio.setIdPago(4L);
        envio.setIdBoleta(4L);

        when(envioRepository.findById(10L)).thenReturn(Optional.of(envio));

        Envio encontrado = envioService.obtenerEnvio(10L);

        assertNotNull(encontrado);
        assertEquals("Ñuñoa", encontrado.getComuna());
    }

    // Test para envío no encontrado
    @Test
    void testObtenerEnvioNotFound() {
        when(envioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EnvioNotFoundException.class, () -> envioService.obtenerEnvio(99L));
    }

    // Test para actualizar envío
    @Test
    void testActualizarEnvio() {
        Envio original = new Envio();
        original.setIdEnvio(5L);
        original.setEstadoEnvio(EstadoEnvio.PENDIENTE);
        original.setFechaDespacho(LocalDate.now());

        Envio actualizado = new Envio();
        actualizado.setEstadoEnvio(EstadoEnvio.ENTREGADO);
        actualizado.setFechaEntrega(LocalDate.now());

        when(envioRepository.findById(5L)).thenReturn(Optional.of(original));
        when(envioRepository.save(any(Envio.class))).thenReturn(actualizado);

        Envio result = envioService.actualizarEnvio(5L, actualizado);

        assertEquals(EstadoEnvio.ENTREGADO, result.getEstadoEnvio());
        assertEquals(actualizado.getFechaEntrega(), result.getFechaEntrega());
    }

    // Test para eliminar envío
    @Test
    void testEliminarEnvio() {
        doNothing().when(envioRepository).deleteById(6L);

        envioService.eliminarEnvio(6L);

        verify(envioRepository, times(1)).deleteById(6L);
    }
}
