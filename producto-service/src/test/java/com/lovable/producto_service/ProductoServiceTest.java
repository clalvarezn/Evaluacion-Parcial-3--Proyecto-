package com.lovable.producto_service;

import com.lovable.producto_service.model.Producto;
import com.lovable.producto_service.repository.ProductoRepository;
import com.lovable.producto_service.service.ProductoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductoServiceTest {

    // 1. Declarar los Mocks (Igual a la guía de Duoc)
    @Mock
    private ProductoRepository productoRepository; // Simula la BD

    @InjectMocks
    private ProductoServiceImpl productoService; // El servicio real que vamos a testear

    @BeforeEach
    void setUp() {
        // Inicializa los mocks antes de cada test
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerProductoPorIdExitoso() {
        // --- 1. CONFIGURACIÓN DEL MOCK (Given) ---
        Producto productoSimulado = new Producto();
        productoSimulado.setIdProducto(1);
        productoSimulado.setNombreProducto("Polera Oversize");
        productoSimulado.setPrecio(15990);
        productoSimulado.setStockActual(20);

        when(productoRepository.findById(1)).thenReturn(Optional.of(productoSimulado));

        // --- 2. LLAMADA AL MÉTODO DEL SERVICIO (When) ---
        // 💡 CAMBIO AQUÍ: Llamamos a obtenerPorId que es el método real de tu ProductoServiceImpl
        // e importamos tu ProductoResponseDTO si es necesario.
        com.lovable.producto_service.dto.ProductoResponseDTO resultado = productoService.obtenerPorId(1);

        // --- 3. VERIFICACIÓN DEL RESULTADO (Then) ---
        assertNotNull(resultado, "El producto devuelto no debería ser nulo");
        assertEquals("Polera Oversize", resultado.getNombreProducto());
        assertEquals(15990, resultado.getPrecio());
        assertEquals(20, resultado.getStockActual());

        verify(productoRepository, times(1)).findById(1);
    }
}
