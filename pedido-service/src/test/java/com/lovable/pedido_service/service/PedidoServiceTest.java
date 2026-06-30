package com.lovable.pedido_service.service;

import com.lovable.pedido_service.client.ProductoClient;
import com.lovable.pedido_service.client.UsuarioClient;
import com.lovable.pedido_service.client.dto.ProductoResponse;
import com.lovable.pedido_service.client.dto.UsuarioResponse;
import com.lovable.pedido_service.enums.EstadoPedido;
import com.lovable.pedido_service.model.Pedido;
import com.lovable.pedido_service.repository.PedidoRepository;
import com.lovable.pedido_service.repository.DetallePedidoRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private DetallePedidoRepository detallePedidoRepository;

    @Mock
    private UsuarioClient usuarioClient;

    @Mock
    private ProductoClient productoClient;

    @InjectMocks
    private PedidoService pedidoService;

    // Crea pedido correcto para el usuario 1, simula estado activo
    @Test
    void testCrearPedido_OK() {

        Pedido pedido = new Pedido();
        pedido.setIdUsuario(1);

        UsuarioResponse usuario = new UsuarioResponse();
        usuario.setEstadoUsuario("ACTIVO");

        when(usuarioClient.obtenerUsuarioPorId(1)).thenReturn(usuario);
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);

        Pedido resultado = pedidoService.crearPedido(pedido);

        assertNotNull(resultado);
        assertEquals(EstadoPedido.BORRADOR, resultado.getEstado());
    }

    // si el usuario no existe falla el pedido
    @Test
    void testCrearPedido_UsuarioNoExiste() {

        Pedido pedido = new Pedido();
        pedido.setIdUsuario(1);

        when(usuarioClient.obtenerUsuarioPorId(1)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () ->
                pedidoService.crearPedido(pedido)
        );
    }

    // Elimina el pedido exitosamente simulando el repositorio
    @Test
    void testEliminarPedido_OK() {

        Pedido pedido = new Pedido();
        pedido.setIdPedido(1);
        pedido.setEstado(EstadoPedido.BORRADOR);

        when(pedidoRepository.findById(1)).thenReturn(Optional.of(pedido));

        pedidoService.eliminarPedido(1);

        verify(pedidoRepository).delete(pedido);
    }

    // simula error de eliminar pedido que esta en estado pagado
    @Test
    void testEliminarPedido_Pagado() {

        Pedido pedido = new Pedido();
        pedido.setEstado(EstadoPedido.PAGADO);

        when(pedidoRepository.findById(1)).thenReturn(Optional.of(pedido));

        assertThrows(IllegalStateException.class, () ->
                pedidoService.eliminarPedido(1)
        );
    }
}
