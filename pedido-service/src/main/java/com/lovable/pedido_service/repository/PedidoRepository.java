package com.lovable.pedido_service.repository;

import com.lovable.pedido_service.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Integer>
{

    List<Pedido> findByIdUsuario(Integer idUsuario);

}
