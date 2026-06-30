package com.lovable.pago_service.service;

import com.lovable.pago_service.model.Pago;
import com.lovable.pago_service.repository.PagoRepository;
import com.lovable.pago_service.client.PedidoClient;
import com.lovable.pago_service.client.dto.PedidoResponse;
import com.lovable.pago_service.enums.EstadoPago;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class PagoServiceTest {

    @Mock
    private PagoRepository pagoRepository;

    @Mock
    private PedidoClient pedidoClient;

    @InjectMocks
    private PagoService pagoService;

    @Test
    void testProcesarPago_OK() {

        // Simula pago para el pedido 1 por 1.000
        Pago pago = new Pago();
        pago.setIdPedido(1);
        pago.setMonto(1000.0);

        //Simula pedido por 1.000
        PedidoResponse pedido = new PedidoResponse();
        pedido.setTotal(1000.0);

        //devuelve pedido y el pago
        when(pedidoClient.obtenerPedidoPorId(1)).thenReturn(pedido);
        when(pagoRepository.save(any(Pago.class))).thenReturn(pago);

        // Ejecuta el metodo real de pago
        Pago resultado = pagoService.procesarPago(pago);

        // Valida resultado no nulo o pago aprobado
        assertNotNull(resultado);
        assertEquals(EstadoPago.APROBADO, resultado.getEstadoPago());

        //revisa que pago se guardara y actualiza estado del pedido
        verify(pagoRepository).save(pago);
        verify(pedidoClient).cambiarEstadoPedido(1);
    }

}
