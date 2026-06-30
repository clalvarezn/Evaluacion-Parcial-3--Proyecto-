package com.lovable.producto_service.repository;

import com.lovable.producto_service.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    // Por ahora no necesitamos métodos personalizados, JpaRepository ya nos da todo el CRUD.


}