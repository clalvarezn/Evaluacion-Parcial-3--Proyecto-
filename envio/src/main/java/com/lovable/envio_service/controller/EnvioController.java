package com.lovable.envio_service.controller;


import com.lovable.envio_service.model.Envio;
import com.lovable.envio_service.service.EnvioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "Envios", description = "Gestión de envíos")
@RestController
@RequestMapping("/api/v1/envios")
public class EnvioController {

    private final EnvioService envioService;

    public EnvioController(EnvioService envioService) {
        this.envioService = envioService;
    }

    // Crear Envío
    @Operation(summary = "Crear envío", description = "Registra un nuevo envío validando Pago y Boleta")
    @ApiResponse(responseCode = "201", description = "Envío creado correctamente")
    @ApiResponse(responseCode = "400", description = "Error de validación o dependencias inválidas")
    @PostMapping
    public ResponseEntity<Envio> crearEnvio(@RequestBody Envio envio) {
        Envio nuevoEnvio = envioService.crearEnvio(envio);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Envío creado correctamente");
        response.put("data", nuevoEnvio);

        return ResponseEntity.ok(nuevoEnvio);
    }

    // Obtener Envío por ID
    @Operation(summary = "Obtener envío por ID", description = "Devuelve un envío específico según su ID")
    @ApiResponse(responseCode = "200", description = "Envío encontrado")
    @ApiResponse(responseCode = "404", description = "Envío no existe")
    @GetMapping("/{idEnvio}")
    public ResponseEntity<Envio> obtenerEnvio(@Parameter(description = "ID del envío") @PathVariable Long idEnvio) {
        Envio envio = envioService.obtenerEnvio(idEnvio);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Envío encontrado");
        response.put("data", envio);

        return ResponseEntity.ok(envio);
    }

    // Listar todos los envíos
    @Operation(summary = "Listar envíos", description = "Devuelve todos los envíos registrados")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    @GetMapping
    public ResponseEntity<List<Envio>> listarEnvios() {

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Liosta obtenida correctamente");
        response.put("data", envioService.listarEnvios());

        return ResponseEntity.ok(envioService.listarEnvios());
    }

    // Actualizar Envío
    @Operation(summary = "Actualizar envío", description = "Modifica los datos de un envío existente")
    @ApiResponse(responseCode = "200", description = "Envío actualizado correctamente")
    @ApiResponse(responseCode = "404", description = "Envío no existe")
    @PutMapping("/{idEnvio}")
    public ResponseEntity<Envio> actualizarEnvio(@Parameter(description = "ID del envío") @PathVariable Long idEnvio,
                                                 @RequestBody Envio envioActualizado) {
        Envio envio = envioService.actualizarEnvio(idEnvio, envioActualizado);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Envío actualizado correctamente");
        response.put("data", envio);

        return ResponseEntity.ok(envio);
    }

    // Eliminar Envío
    @Operation(summary = "Eliminar envío", description = "Elimina un envío por su ID")
    @ApiResponse(responseCode = "204", description = "Envío eliminado correctamente")
    @ApiResponse(responseCode = "404", description = "Envío no existe")
    @DeleteMapping("/{idEnvio}")
    public ResponseEntity<Void> eliminarEnvio(@Parameter(description = "ID del envío") @PathVariable Long idEnvio) {
        envioService.eliminarEnvio(idEnvio);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Envío eliminado correctamente");

        return ResponseEntity.noContent().build();
    }
}
