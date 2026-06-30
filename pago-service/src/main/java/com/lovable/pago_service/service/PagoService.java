package com.lovable.pago_service.service;

import com.lovable.pago_service.client.PedidoClient;
import com.lovable.pago_service.client.dto.PedidoResponse;
import com.lovable.pago_service.enums.EstadoPago;
import com.lovable.pago_service.model.Pago;
import com.lovable.pago_service.repository.PagoRepository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class PagoService {


    private static final Logger logger = LoggerFactory.getLogger(PagoService.class);

    private final PagoRepository pagoRepository;
    private final PedidoClient pedidoClient;

    public PagoService(PagoRepository pagoRepository, PedidoClient pedidoClient) {
        this.pagoRepository = pagoRepository;
        this.pedidoClient = pedidoClient;
    }

    // Crea Pago
    public Pago procesarPago(Pago pago) {

        logger.info("Procesando pago para el pedido: {}", pago.getIdPedido());

        //Obtiene el pedido
        PedidoResponse pedido = pedidoClient.obtenerPedidoPorId(pago.getIdPedido());


        // valida si existe el pedido
        if (pedido == null) {
            throw new IllegalArgumentException("El pedido no existe");
        }

        // valida si el monto coincide con el total
        if (!pago.getMonto().equals(pedido.getTotal())) {

            logger.warn("Monto incorrecto. Total esperado: {}", pedido.getTotal());

            throw new IllegalStateException(
                    "El monto no coincide con el total del pedido: $" + pedido.getTotal()
            );
        }


        //Asignar fecha
        pago.setFechaPago(LocalDateTime.now());

        //Simulación de pago exitoso
        pago.setEstadoPago(EstadoPago.APROBADO);

        //Guardar pago
        Pago pagoGuardado = pagoRepository.save(pago);

        logger.info("Pago registrado correctamente con ID: {}", pagoGuardado.getIdPago());

        //INTEGRACIÓN CON PEDIDO-SERVICE
        pedidoClient.cambiarEstadoPedido(pago.getIdPedido());

        logger.info("Estado del pedido actualizado a PAGADO: {}", pago.getIdPedido());

        return pagoGuardado;
    }

    //Metodo muestra los pagos
    public List<Pago> obtenerPagos() {
        return pagoRepository.findAll();
    }

    //Muestra pagos por Id
    public Pago obtenerPagoPorId(Integer idPago) {
        return pagoRepository.findById(idPago)
                .orElseThrow(() -> new IllegalArgumentException("Pago no encontrado"));
    }


}
