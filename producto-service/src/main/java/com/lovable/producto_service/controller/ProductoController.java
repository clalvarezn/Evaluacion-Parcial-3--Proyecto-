package com.lovable.producto_service.controller;

import com.lovable.producto_service.dto.ProductoRequestDTO;
import com.lovable.producto_service.dto.ProductoResponseDTO; // Importamos tu DTO de respuesta
import com.lovable.producto_service.service.ProductoServiceImpl;
import io.swagger.v3.oas.annotations.Operation; //este el el controller de Swagger de Operation
import io.swagger.v3.oas.annotations.tags.Tag; //este es el controller de Swagger de Tag
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/v1/productos")
@Tag(name = "productos", description = "Documentacion productos")
public class ProductoController {

    @Autowired
    private ProductoServiceImpl productoService;

    // 1. OBTENER TODOS (Retorna lista de DTOs)
    @GetMapping
    @Operation(summary = "Obtener todos los productos", description = "Documentacion Swagger de productos")
    public ResponseEntity<List<ProductoResponseDTO>> lista(){
        List<ProductoResponseDTO> productos = productoService.obtenerTodos();
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }

    // 2. CREAR PRODUCTO (Retorna el DTO del producto creado)
    @PostMapping
    public ResponseEntity<ProductoResponseDTO> guardar(@RequestBody @Valid ProductoRequestDTO request) {
        ProductoResponseDTO productoNuevo = productoService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoNuevo);
    }

    // 3. BUSCAR POR ID (Retorna el DTO)
    @GetMapping("/{idProducto}")
    public ResponseEntity<ProductoResponseDTO> buscar(@PathVariable Integer idProducto){
        try{
            ProductoResponseDTO producto = productoService.obtenerPorId(idProducto);
            return ResponseEntity.ok(producto);
        } catch (Exception e ){
            return ResponseEntity.notFound().build();
        }
    }

    // 4. ACTUALIZAR (Retorna el DTO actualizado)
    @PutMapping("/{idProducto}")
    public ResponseEntity<ProductoResponseDTO> actualizar(@PathVariable Integer idProducto, @RequestBody @Valid ProductoRequestDTO request) {
        try {
            ProductoResponseDTO productoActualizado = productoService.actualizar(idProducto, request);
            return ResponseEntity.ok(productoActualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. ELIMINAR (Se mantiene igual, no retorna cuerpo)
    @DeleteMapping("/{idProducto}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer idProducto) {
        try {
            productoService.desactivarProducto(idProducto);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 6. ACTUALIZAR STOCK (Se mantiene igual)
    @PatchMapping("/{idProducto}/stock")
    public ResponseEntity<?> modificarStock(
            @PathVariable Integer idProducto,
            @RequestParam Integer cantidad) {
        try {
            productoService.actualizarStock(idProducto, cantidad);
            return ResponseEntity.ok().body("Stock actualizado correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}