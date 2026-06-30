package com.example.usuario.service;


import com.example.usuario.exception.UsuarioAlreadyExistsException;
import com.example.usuario.exception.UsuarioNotFoundException;
import com.example.usuario.model.Usuario;
import com.example.usuario.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Transactional
public class UsuarioService {

    private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);
    @Autowired
    private UsuarioRepository usuarioRepository;

    //Metodo para crear un usuario
    public Usuario saveUsuario(Usuario usuario) {
        if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
            throw new UsuarioAlreadyExistsException("El correo ya está registrado");
        }
        log.info("Usuario creado con correo {}", usuario.getCorreo());
        return usuarioRepository.save(usuario);

    }

    //Metodo para obtener todos los usuario
    public List<Usuario> getUsuarios(){
        return usuarioRepository.findAll();}

    //Metodo para obtener un usuario por su id
    public Usuario getUsuario(Long idUsuario){
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario con id :" + idUsuario + "no encontrado"));

    }

    //Metodo para Modificar Usuario
    public Usuario updateUsuario(Long idUsuario, Usuario usuarioAct) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado"));

        usuario.setNombreUsuario(usuarioAct.getNombreUsuario());
        usuario.setCorreo(usuarioAct.getCorreo());
        usuario.setContrasena(usuarioAct.getContrasena());
        usuario.setTelefono(usuarioAct.getTelefono());
        usuario.setEstadoUsuario(usuarioAct.getEstadoUsuario());

        Usuario modificado = usuarioRepository.save(usuario);
        log.info("Usuario modificado con id {}", idUsuario);
        return modificado;
    }

    //Metodo para eliminar un Usuario
    public void deleteUsuario(Long id){
        if (!usuarioRepository.existsById(id)){
            throw new UsuarioNotFoundException("Usuario no encontrado");

        }
        log.info("Usuario eliminado correctamente con id {}", id);
        usuarioRepository.deleteById(id);
    }



}
