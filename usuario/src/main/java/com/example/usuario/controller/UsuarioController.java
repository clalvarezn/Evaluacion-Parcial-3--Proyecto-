package com.example.usuario.controller;


import com.example.usuario.model.Usuario;
import com.example.usuario.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Usuarios", description = "Gestion de usuarios")
@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    //Crear Usuario

    //Swagger
    @Operation(summary = "Crear usuario", description = "Registra un nuevo usuario")
    @ApiResponse(responseCode = "201", description = "Usuario creado correctamente")
    @ApiResponse(responseCode = "400", description = "Error de validación")
    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@Valid @RequestBody Usuario usuario){
        Usuario nuevo = usuarioService.saveUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    //Obtener todos los usuarios

    @Operation(summary = "Listar usuarios", description = "Devuelve todos los usuarios registrados")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerUsuarios(){
        return ResponseEntity.ok(usuarioService.getUsuarios());
    }

    //Obtener Usuario por id

    @Operation(summary = "Obtener usuario por ID", description = "Devuelve un usuario específico")
    @ApiResponse(responseCode = "200", description = "Usuario encontrado")
    @ApiResponse(responseCode = "404", description = "Usuario no existe")
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuario(@Parameter(description = "ID del usuario") @PathVariable Long id){
        return ResponseEntity.ok(usuarioService.getUsuario(id));
    }

    //Modificar Usuario

    @Operation(summary = "Modificar usuario", description = "Actualiza datos de un usuario existente")
    @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente")
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> modificarUsuario(@Parameter(description = "ID del usuario") @PathVariable Long id,
                                                    @Valid @RequestBody Usuario usuario){
        Usuario actualizado = usuarioService.updateUsuario(id, usuario);
        return ResponseEntity.ok(actualizado);
    }

    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario por ID")
    @ApiResponse(responseCode = "204", description = "Usuario eliminado correctamente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@Parameter(description = "ID del usuario") @PathVariable Long id){
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
