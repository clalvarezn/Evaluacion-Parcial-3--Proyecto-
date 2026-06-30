package com.lovable.producto_service.service;

import com.lovable.producto_service.dto.ProductoRequestDTO;
import com.lovable.producto_service.dto.ProductoResponseDTO; // Importamos el DTO de respuesta
import com.lovable.producto_service.model.Producto;
import com.lovable.producto_service.repository.ProductoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProductoServiceImpl implements IProductoService {

    private final ProductoRepository productoRepository;

    // 1. OBTENER TODOS: Convierte la lista de entidades a lista de DTOs
    @Override
    public List<ProductoResponseDTO> obtenerTodos() {
        return productoRepository.findAll()
                .stream()
                .map(this::convertirADTO) // Transformamos cada entidad usando el mapeador manual
                .collect(Collectors.toList());
    }

    // 2. OBTENER POR ID: Busca, y si existe lo transforma a DTO
    @Override
    public ProductoResponseDTO obtenerPorId(Integer idProducto) {

        log.info("==> PETICIÓN: Buscando producto con ID: {}", idProducto);

        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con el ID: " + idProducto));
        return convertirADTO(producto);
    }

    // 3. Método CREAR: Recibe el DTO, guarda la entidad y responde con el DTO de salida
    @Override
    public ProductoResponseDTO crear(ProductoRequestDTO request) {

        log.info("==> PETICIÓN: Creando nuevo producto: {}", request.getNombreProducto());

        Producto producto = new Producto();
        producto.setNombreProducto(request.getNombreProducto());
        producto.setDescripcion(request.getDescripcion());
        producto.setTalla(request.getTalla());
        producto.setColor(request.getColor());
        producto.setPrecio(request.getPrecio());
        producto.setImagen(request.getImagen());
        producto.setEstadoProducto(request.getEstadoProducto());
        producto.setStockActual(request.getStockActual());

        Producto productoGuardado = productoRepository.save(producto);

        // 1. Guardamos el DTO transformado en una variable primero
        ProductoResponseDTO respuesta = convertirADTO(productoGuardado);

        // 2. Ahora el log puede leer la variable 'respuesta' sin problemas
        log.info("<== RESPUESTA: Producto creado exitosamente con ID: {}", respuesta.getIdProducto());

        // 3. Finalmente hacemos el return al último de todo el método
        return respuesta;
    }

    // 4. Método ACTUALIZAR: Modifica la entidad existente y retorna su DTO
    @Override
    public ProductoResponseDTO actualizar(Integer idProducto, ProductoRequestDTO request) {
        // Buscamos directo en el repositorio para obtener la entidad interna que vamos a modificar
        Producto productoExistente = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con el ID: " + idProducto));

        productoExistente.setNombreProducto(request.getNombreProducto());
        productoExistente.setDescripcion(request.getDescripcion());
        productoExistente.setTalla(request.getTalla());
        productoExistente.setColor(request.getColor());
        productoExistente.setPrecio(request.getPrecio());
        productoExistente.setImagen(request.getImagen());
        productoExistente.setEstadoProducto(request.getEstadoProducto());
        productoExistente.setStockActual(request.getStockActual());

        Producto productoActualizado = productoRepository.save(productoExistente);
        return convertirADTO(productoActualizado);
    }

    // 5. Método ACTUALIZAR STOCK (Se mantiene con void, solo cambia el buscar interno)
    @Override
    public void actualizarStock(Integer idProducto, Integer cantidad) {

        //acá va el log de actualización de producto
        log.info("==> PETICIÓN STOCK: Modificando stock del producto ID: {} en cantidad: {}", idProducto, cantidad);

        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con el ID: " + idProducto));

        int nuevoStock = producto.getStockActual() + cantidad;

        if (nuevoStock < 0) {

            // Usamos log error para alertar que algo falló en las reglas del negocio
            log.error("❌ ERROR STOCK: Intento de stock negativo para producto ID: {}", idProducto);

            throw new RuntimeException("No hay suficiente stock para el producto: " + producto.getNombreProducto());
        }

        producto.setStockActual(nuevoStock);
        productoRepository.save(producto);

        log.info("<== RESPUESTA STOCK: Stock actualizado con éxito. Nuevo stock: {}", nuevoStock);
    }

    // 6. DESACTIVAR PRODUCTO
    @Override
    public void desactivarProducto(Integer idProducto) {
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con el ID: " + idProducto));
        producto.setEstadoProducto(false);
        productoRepository.save(producto);
    }

    // ==========================================
    // 🛠️ MÉTODO AUXILIAR TRANSFORMADOR (MAPPER)
    // ==========================================
    private ProductoResponseDTO convertirADTO(Producto producto) {
        ProductoResponseDTO dto = new ProductoResponseDTO();
        dto.setIdProducto(producto.getIdProducto());
        dto.setNombreProducto(producto.getNombreProducto());
        dto.setDescripcion(producto.getDescripcion());
        dto.setTalla(producto.getTalla());
        dto.setColor(producto.getColor());
        dto.setPrecio(producto.getPrecio());
        dto.setImagen(producto.getImagen());
        dto.setEstadoProducto(producto.getEstadoProducto());
        dto.setStockActual(producto.getStockActual());
        return dto;
    }
}