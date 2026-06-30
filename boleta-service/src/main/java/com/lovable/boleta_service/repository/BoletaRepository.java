package com.lovable.boleta_service.repository;

import com.lovable.boleta_service.model.Boleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoletaRepository extends JpaRepository<Boleta, Integer> {

    // JpaRepository ya nos da todo el CRUD.

}
