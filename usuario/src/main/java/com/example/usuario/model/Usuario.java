package com.example.usuario.model;


import com.example.usuario.enums.EstadoUsuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @NotBlank(message = "Debe ingresar un nombre de usuario")
    @Column(nullable = false)
    private String nombreUsuario;

    @Email
    @NotBlank(message = "Debe ingresar un correo")
    @Column(nullable = false, unique = true)
    private String correo;

    @Size(min = 8 , message = "La contraseña debe tener al menos 8 caracteres")
    @Column(nullable = false)
    private String contrasena;

    private String telefono;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoUsuario estadoUsuario;

}
