package com.lovable.pedido_service.controller;

import com.lovable.pedido_service.dto.EstadoPedidoRequest;
import com.lovable.pedido_service.model.Pedido;
import com.lovable.pedido_service.service.PedidoService;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.lovable.pedido_service.dto.ProductoRequest;

import java.util.List;

@RestController
@RequestMapping("api/v1/pedidos")
@Tag(name="Pedidos", description="API para la gestion de pedidos")

public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    @Operation(summary = "Obtener todos los pedidos")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de Pedidos obtenida"),
        @ApiResponse(responseCode = "204", description = "No se encontraron Pedidos")
    })
    public ResponseEntity<List<Pedido>> obtenerPedidos() {
        List<Pedido> pedidos = pedidoService.obtenerPedidos();
        if (pedidos.isEmpty()) {
            return ResponseEntity.ok()
                            .header("X-Total-Count", String.valueOf(pedidos.size()))
                            .header("X-Service-Name", "pedido-service")
                            .body(pedidos);

        }
        return ResponseEntity.ok()
            .header("X-Total-Count", String.valueOf(pedidos.size()))
            .header("X-Service-Name", "pedido-service")
            .body(pedidos);
    }


    @GetMapping("/{idPedido}")
    @Operation(summary = "Obtener pedido por ID con detalle")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "404", description = "No se encontro el Pedido")
    })
    public ResponseEntity<Pedido> obtenerPedidoPorId(@PathVariable Integer idPedido) {
        Pedido pedido = pedidoService.obtenerPedidoPorId(idPedido);

        return ResponseEntity.ok()
                .header("X-Service-Name", "pedido-service")
                .body(pedido);
    }

    @GetMapping("/usuarios/{idUsuario}")
    @Operation(summary = "Obtener pedido del usuario por ID con detalle")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedidos encontrados del usuario"),
            @ApiResponse(responseCode = "204", description = "No se encontro Pedidos para el usuario")
    })
    public ResponseEntity<List<Pedido>> obtenerPedidosPorIdUsuario(@PathVariable Integer idUsuario) {
        List<Pedido> pedidos = pedidoService.obtenerPedidosPorIdUsuario(idUsuario);
        if (pedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(pedidos.size()))
                .header("X-Service-Name", "pedido-service")
                .body(pedidos);
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo pedido")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error en los datos del pedido")
    })
    public ResponseEntity<Pedido> crearPedido(@RequestBody Pedido pedido) {

        Pedido nuevoPedido = pedidoService.crearPedido(pedido);

        return ResponseEntity.status(201)
                .header("X-Service-Name", "pedido-service")
                .body(nuevoPedido);
    }


    @PostMapping("/{idPedido}/productos")
    @Operation(summary = "Agregar producto a un pedido")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto agregado al pedido"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos")
    })
    public ResponseEntity<String> agregarProducto(@PathVariable Integer idPedido,
                                                  @RequestBody ProductoRequest request) {

        pedidoService.agregarProducto(idPedido,
                request.getIdProducto(),
                request.getCantidadProducto());


        return ResponseEntity.status(201)
                .header("X-Service-Name", "pedido-service")
                .body("Producto agregado correctamente al pedido");
    }

    @PutMapping("/{idPedido}")
    @Operation(summary = "Actualizar un pedido")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado"),
            @ApiResponse(responseCode = "400", description = "Pedido no puede ser modificado")

    })
    public ResponseEntity<Pedido> actualizarPedido(@PathVariable Integer idPedido, @RequestBody Pedido pedido) {
        Pedido pedidoExistente = pedidoService.obtenerPedidoPorId(idPedido);

        pedido.setIdPedido(idPedido);
        pedido.setEstado(pedidoExistente.getEstado());
        pedido.setTotal(pedidoExistente.getTotal());
        pedido.setFechaPedido(pedidoExistente.getFechaPedido());

        Pedido pedidoActualizado = pedidoService.actualizarPedido(pedido);

        return ResponseEntity.ok()
                .header("X-Service-Name", "pedido-service")
                .body(pedidoActualizado);
    }

    @PutMapping("/detalle/{idDetalle}")
    @Operation(summary = "Actualizar producto de un pedido")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Detalle no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<String> actualizarProducto(@PathVariable Integer idDetalle,
                                                     @RequestBody ProductoRequest request) {
            pedidoService.actualizarProducto(
                    idDetalle,
                    request.getIdProducto(),
                    request.getCantidadProducto()
            );

        return ResponseEntity.ok()
                .header("X-Service-Name", "pedido-service")
                .body("Producto actualizado correctamente");
    }

    @PutMapping("/{idPedido}/estado")
    @Operation(summary = "Cambiar estado de un pedido")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "El Estado del pedido se actualizo correctamente"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado"),
            @ApiResponse(responseCode = "400", description = "No se puede cambiar el estado del pedido")
    })
    public ResponseEntity<Void> cambiarEstadoPedido(@PathVariable Integer idPedido,
                                                    @RequestBody EstadoPedidoRequest request) {

        pedidoService.cambiarEstadoPedido(idPedido, request.getNuevoEstado());

        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/detalle/{idDetalle}")
    @Operation(summary = "Eliminar producto de un pedido")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Producto eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Detalle no encontrado"),
            @ApiResponse(responseCode = "400", description = "No se puede eliminar el detalle del producto")
    })
    public ResponseEntity<String> eliminarProducto(@PathVariable Integer idDetalle) {

        pedidoService.eliminarProducto(idDetalle);
        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{idPedido}")
    @Operation(summary = "Eliminar un pedido")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pedido eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado"),
            @ApiResponse(responseCode = "400", description = "No se puede eliminar el pedido")
    })
    public ResponseEntity<String> eliminarPedido(@PathVariable Integer idPedido) {

        pedidoService.eliminarPedido(idPedido);
        return ResponseEntity.noContent().build();
    }

}
