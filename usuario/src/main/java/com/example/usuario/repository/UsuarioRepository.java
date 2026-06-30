package com.example.usuario.repository;

import com.example.usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    //metodos adicionbales :
    boolean existsByCorreo(String corrreo);

}
