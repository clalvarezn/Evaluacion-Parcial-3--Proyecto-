package com.lovable.producto_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProductoRequestDTO {

    //estos datos los va a llenar el usuario, he quitado el idProducto ya que es autogenerado

    @NotBlank(message = "El nombre es obligatorio")
    private String nombreProducto;

    @NotBlank(message = "La descripcion es obligatoria")
    private String descripcion;

    @NotBlank(message = "La talla es obligatoria")
    private String talla;

    @NotBlank(message = "El color es obligatorio")
    private String color;

    @NotNull(message = "El precio es obligatorio")
    @Min(value = 1, message = "El precio debe ser mayor a cero")
    private Integer precio;

    @NotBlank(message = "Debe especificar una URL de la imagen")
    private String imagen;

    @NotNull(message = "Debe poner un estado 1 habilitado o 0 no habilitado")
    private Boolean estadoProducto;

    @NotNull(message = "El stock es obligatorio y no debe ser cero")
    @Min(value = 0, message = "El Stock debe ser mayor a cero")
    private Integer stockActual;

}
