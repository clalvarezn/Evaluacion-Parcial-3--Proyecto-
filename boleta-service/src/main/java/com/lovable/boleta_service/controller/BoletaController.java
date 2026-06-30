package com.lovable.boleta_service.controller;

import com.lovable.boleta_service.dto.BoletaRequestDTO;
import com.lovable.boleta_service.dto.BoletaResponseDTO; // Importamos tu DTO de respuesta
import com.lovable.boleta_service.service.BoletaServiceImpl;
import io.swagger.v3.oas.annotations.Operation; //este el el controller de Swagger de Operation
import io.swagger.v3.oas.annotations.tags.Tag; //este es el controller de Swagger de Tag
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/boletas")
@Tag(name = "boletas", description = "Documentacion boletas")
public class BoletaController {

    @Autowired
    private BoletaServiceImpl boletaService;

    // 1. OBTENER TODOS (Retorna lista de DTOs)
    @GetMapping
    @Operation(summary = "Obtener todas las boletas", description = "Documentacion Swagger de boletas")
    public ResponseEntity<List<BoletaResponseDTO>> lista(){
        List<BoletaResponseDTO> boletas = boletaService.obtenerTodos();
        if (boletas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(boletas);
    }

    // 2. CREAR PRODUCTO (Retorna el DTO del producto creado)
    @PostMapping
    public ResponseEntity<BoletaResponseDTO> guardar(@RequestBody @Valid BoletaRequestDTO request) {
        BoletaResponseDTO boletaNueva = boletaService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(boletaNueva);
    }

    // 3. BUSCAR POR ID (Retorna el DTO)
    @GetMapping("/{idBoleta}")
    public ResponseEntity<BoletaResponseDTO> buscar(@PathVariable Integer idBoleta){
        try{
            BoletaResponseDTO boleta = boletaService.obtenerPorId(idBoleta);
            return ResponseEntity.ok(boleta);
        } catch (Exception e ){
            return ResponseEntity.notFound().build();
        }
    }

}
