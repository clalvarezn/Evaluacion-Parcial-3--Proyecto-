package com.lovable.pedido_service.service;


import com.lovable.pedido_service.client.dto.UsuarioResponse;
import com.lovable.pedido_service.enums.EstadoPedido;
import com.lovable.pedido_service.exception.DetalleNotFoundException;
import com.lovable.pedido_service.exception.PedidoNotFoundException;
import com.lovable.pedido_service.model.DetallePedido;
import com.lovable.pedido_service.model.Pedido;
import com.lovable.pedido_service.repository.DetallePedidoRepository;
import com.lovable.pedido_service.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lovable.pedido_service.client.UsuarioClient;
import com.lovable.pedido_service.client.ProductoClient;
import com.lovable.pedido_service.client.dto.ProductoResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class PedidoService {

    private static final Logger logger = LoggerFactory.getLogger(PedidoService.class);

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;


    @Autowired
    private UsuarioClient usuarioClient;

    @Autowired
    private ProductoClient productoClient;



    //--Metodo crearPedido
    public Pedido crearPedido(Pedido pedido) {

        logger.info("Creando pedido para el usuario: {}", pedido.getIdUsuario());

        UsuarioResponse usuario = usuarioClient.obtenerUsuarioPorId(pedido.getIdUsuario());

        //Microservicio que valida si el usuario existe
        if (usuario == null) {
            logger.error("Usuario no existe: {}", pedido.getIdUsuario());
            throw new IllegalArgumentException("El usuario no existe");
        }

        //Microservicio que valida si el usuario esta activo
        if (!"ACTIVO".equalsIgnoreCase(usuario.getEstadoUsuario())) {
            logger.warn("Usuario no esta activo: {}", pedido.getIdUsuario());
            throw new IllegalStateException("El usuario no esta activo");
        }

        pedido.setFechaPedido(LocalDateTime.now());
        pedido.setEstado(EstadoPedido.BORRADOR);
        pedido.setTotal(0.0);

        return pedidoRepository.save(pedido);
    }

    //--Metodo actualizarPedido
    public Pedido actualizarPedido(Pedido pedido) {

        //Metodo que valida si el pedido existe
        Pedido pedidoExistente = pedidoRepository.findById(pedido.getIdPedido())
                .orElseThrow(() -> new PedidoNotFoundException("Pedido no encontrado"));

        //Metodo que valida si el pedido a actualizar ya fue pagado
        if (pedidoExistente.getEstado() == EstadoPedido.PAGADO) {
            throw new IllegalStateException("No se puede actualizar un pedido PAGADO");
        }


        pedido.setEstado(pedidoExistente.getEstado());
        pedido.setTotal(pedidoExistente.getTotal());
        pedido.setFechaPedido(pedidoExistente.getFechaPedido());

        return pedidoRepository.save(pedido);
    }

    //--Metodo eliminarPedido
    public void eliminarPedido(Integer idPedido) {

        logger.info("Intentando eliminar pedido: {}", idPedido);

        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new PedidoNotFoundException("Pedido no encontrado"));

        if (pedido.getEstado() == EstadoPedido.PAGADO) {
            logger.warn("No se puede eliminar pedido PAGADO: {}", idPedido);
            throw new IllegalStateException("No se puede eliminar un pedido PAGADO");
        }

        pedidoRepository.delete(pedido);

        logger.info("Pedido eliminado correctamente: {}", idPedido);

    }

    //--Metodo obtener Pedido por ID
    public Pedido obtenerPedidoPorId(Integer idPedido) {

        return pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new PedidoNotFoundException("Pedido no encontrado"));
    }

    //--Metodo obtener Pedidos por ID de Usuario
    public List<Pedido> obtenerPedidosPorIdUsuario(Integer idUsuario) {
        return pedidoRepository.findByIdUsuario(idUsuario);
    }

    //--Metodo obtener Pedidos
    public List<Pedido> obtenerPedidos() {
        return pedidoRepository.findAll();
    }

    //--Metodo agregarProducto
    public void agregarProducto(Integer idPedido, Integer idProducto, Integer cantidadProducto) {

        logger.info("Agregando producto {} al pedido: {}", idProducto, idPedido);

        Pedido pedido = pedidoRepository.findById(idPedido).orElseThrow(() -> new PedidoNotFoundException("Pedido no encontrado"));

        if (pedido.getEstado() == EstadoPedido.PAGADO) {
            logger.warn("Intento de modificar pedido PAGADO: {}", idPedido);
            throw new IllegalStateException("No se puede agregar productos a un pedido PAGADO");
        }

        ProductoResponse producto = productoClient.obtenerProductoPorId(idProducto);

        //Microservicio que valida si el producto existe
        if (producto == null) {
            logger.error("Producto no encontrado: {}", idProducto);
            throw new IllegalArgumentException("Producto no encontrado");
        }

        //Microservicio que valida stock del producto
        if (producto.getStockActual() < cantidadProducto) {
            logger.warn("Stock insuficiente para el producto: {}", idProducto);
            throw new IllegalStateException("Stock insuficiente");
        }

        detallePedidoRepository.save(
                new DetallePedido(pedido, idProducto, cantidadProducto, producto.getPrecio())
        );

        calcularTotal(idPedido);

        logger.info("Producto agregado correctamente al pedido: {}", idPedido);

    }

    //--Metodo actualizarProducto
    public void actualizarProducto(Integer idDetalle, Integer idProducto, Integer cantidadProducto) {
        DetallePedido detallePedido = detallePedidoRepository.findById(idDetalle).orElseThrow(() -> new DetalleNotFoundException("Detalle de pedido no encontrado"));

        if (detallePedido.getPedido().getEstado() == EstadoPedido.PAGADO) {
            throw new IllegalStateException("No se puede modificar un pedido PAGADO");
        }


        //Obtiene el producto desde productoServicio
        ProductoResponse producto = productoClient.obtenerProductoPorId(idProducto);

        //Valida si existe
        if (producto == null) {
            throw new IllegalArgumentException("Producto no encontrado");
        }

        //Valida que tenga stock
        if (producto.getStockActual() < cantidadProducto) {
            throw new IllegalStateException("Stock insuficiente");
        }


        detallePedido.setIdProducto(idProducto);
        detallePedido.setCantidadProducto(cantidadProducto);
        detallePedido.setPrecioProducto(producto.getPrecio());
        detallePedidoRepository.save(detallePedido);
        calcularTotal(detallePedido.getPedido().getIdPedido());

    }

    //--Metodo eliminarProducto
    public void eliminarProducto(Integer idDetalle) {


        DetallePedido detallePedido = detallePedidoRepository.findById(idDetalle).orElseThrow(() -> new DetalleNotFoundException("Detalle de pedido no encontrado"));

        if (detallePedido.getPedido().getEstado() == EstadoPedido.PAGADO) {
            throw new IllegalStateException("No se puede eliminar un producto de un pedido PAGADO");
        }

        Integer idPedido = detallePedido.getPedido().getIdPedido();
        detallePedidoRepository.deleteById(idDetalle);
        calcularTotal(idPedido);
    }

    //Metodo calcularTotal del pedido
    public void calcularTotal(Integer idPedido) {
        Pedido pedido = pedidoRepository.findById(idPedido).orElseThrow(() -> new PedidoNotFoundException("Pedido no encontrado"));

        List<DetallePedido> detalles = detallePedidoRepository.findByPedido_IdPedido(idPedido);

        //recorre detalle de lista y multiplica cantidad * producto, luego suma resultados
        Double total = detalles.stream()
                .mapToDouble(detalle -> detalle.getCantidadProducto() * detalle.getPrecioProducto()).sum();

        pedido.setTotal(total);
        pedidoRepository.save(pedido);
    }

    //Metodo cambiarEstadoPedido
    public void cambiarEstadoPedido(Integer idPedido, EstadoPedido nuevoEstado) {

        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new PedidoNotFoundException("Pedido no encontrado"));

        if (pedido.getEstado() == EstadoPedido.PAGADO) {
            throw new IllegalStateException("No se puede cambiar el estado de un pedido PAGADO");
        }

        if (nuevoEstado == null) {
            throw new IllegalArgumentException("El estado no puede ser nulo");
        }

        // Obliga a que el pedido sea SOlicitado
        if (pedido.getEstado() == EstadoPedido.BORRADOR
                && nuevoEstado == EstadoPedido.PAGADO) {
            throw new IllegalStateException("Debe ser SOLICITADO antes de PAGADO");
        }

        pedido.setEstado(nuevoEstado);
        pedidoRepository.save(pedido);
    }


}
