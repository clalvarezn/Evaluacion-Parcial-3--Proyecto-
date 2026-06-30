package com.lovable.pedido_service.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "detalle_pedido")
@Data
@NoArgsConstructor
public class DetallePedido {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private int idDetalle;

    @ManyToOne
    @JoinColumn(name = "id_pedido", nullable = false)
    @JsonBackReference // Indica que esta es la parte que NO se serializa para evitar el bucle
    @ToString.Exclude
    @NotNull
    private Pedido pedido;

    @NotNull
    @Column(name = "id_producto", nullable = false)
    private Integer idProducto;

    @NotNull
    @Positive
    @Column(name = "cantidad_producto", nullable = false)
    private Integer cantidadProducto;

    @NotNull
    @Positive
    @Column(name = "precio_producto", nullable = false)
    private Double precioProducto;

    public DetallePedido(Pedido pedido, Integer idProducto, Integer cantidadProducto, Double precioProducto) {
        this.pedido = pedido;
        this.idProducto = idProducto;
        this.cantidadProducto = cantidadProducto;
        this.precioProducto = precioProducto;
    }



}
