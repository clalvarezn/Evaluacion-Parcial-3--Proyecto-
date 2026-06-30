package com.lovable.pago_service.repository;

import com.lovable.pago_service.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface PagoRepository extends JpaRepository<Pago, Integer> {
}
