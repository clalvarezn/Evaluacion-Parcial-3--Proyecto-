package com.lovable.pedido_service.repository;

import com.lovable.pedido_service.model.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Integer>
{

    List<DetallePedido> findByPedido_IdPedido(Integer idPedido);

}
