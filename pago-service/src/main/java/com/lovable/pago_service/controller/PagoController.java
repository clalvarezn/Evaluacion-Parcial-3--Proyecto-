package com.lovable.pago_service.controller;

import com.lovable.pago_service.model.Pago;
import com.lovable.pago_service.service.PagoService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pagos")
@Tag(name="Pagos", description="API para la gestion de pagos")
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    // Muestra pagos existentes
    @GetMapping
    @Operation(summary = "Obtener todos los pagos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pagos Obtenidos"),
            @ApiResponse(responseCode = "204", description = "No se encontraron Pagos")
    })
    public ResponseEntity<List<Pago>> obtenerPagos() {
        List<Pago> pagos = pagoService.obtenerPagos();
        if (pagos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pagos);
    }

    //Muestra pagos por IdPago
    @GetMapping("/{idPago}")
    @Operation(summary = "Obtener pagos por ID de Pago")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pago encontrado"),
            @ApiResponse(responseCode = "404", description = "No se encontro el Pago")
    })
    public ResponseEntity<Pago> obtenerPagoPorId(@PathVariable Integer idPago) {

        Pago pago = pagoService.obtenerPagoPorId(idPago);

        return ResponseEntity.ok(pago);
    }

    //Crear pago
    @PostMapping
    @Operation(summary = "Crear un Pago")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pago creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error en el pago")
    })
    public ResponseEntity<Pago> procesarPago(@RequestBody Pago pago) {

        Pago pagoProcesado = pagoService.procesarPago(pago);

        return ResponseEntity.status(201)
                .header("X-Service-Name", "pago-service")
                .body(pagoProcesado);

    }
}
