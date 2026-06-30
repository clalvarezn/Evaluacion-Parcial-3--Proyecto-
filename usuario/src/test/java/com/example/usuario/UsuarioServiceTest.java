package com.example.usuario;


import com.example.usuario.enums.EstadoUsuario;
import com.example.usuario.exception.UsuarioAlreadyExistsException;
import com.example.usuario.exception.UsuarioNotFoundException;
import com.example.usuario.model.Usuario;
import com.example.usuario.repository.UsuarioRepository;
import com.example.usuario.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @MockBean
    private UsuarioRepository usuarioRepository;

    // Test para crear usuario
    @Test
    void testSaveUsuario() {
        Usuario usuario = new Usuario(1L, "Gabriel", "gabriel@mail.com", "12345678", "987654321", EstadoUsuario.ACTIVO);

        when(usuarioRepository.existsByCorreo(usuario.getCorreo())).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario guardado = usuarioService.saveUsuario(usuario);

        assertNotNull(guardado);
        assertEquals("Gabriel", guardado.getNombreUsuario());
        assertEquals("gabriel@mail.com", guardado.getCorreo());
    }

    // Test para validar correo duplicado
    @Test
    void testSaveUsuarioCorreoDuplicado() {
        Usuario usuario = new Usuario(2L, "Ana", "ana@mail.com", "abcd1234", "123456789", EstadoUsuario.ACTIVO);

        when(usuarioRepository.existsByCorreo(usuario.getCorreo())).thenReturn(true);

        assertThrows(UsuarioAlreadyExistsException.class, () -> usuarioService.saveUsuario(usuario));
    }

    // Test para obtener todos los usuarios
    @Test
    void testGetUsuarios() {
        when(usuarioRepository.findAll())
                .thenReturn(List.of(new Usuario(3L, "Pedro", "pedro@mail.com", "pass1234", "111222333", EstadoUsuario.ACTIVO)));

        List<Usuario> usuarios = usuarioService.getUsuarios();

        assertNotNull(usuarios);
        assertEquals(1, usuarios.size());
        assertEquals("Pedro", usuarios.get(0).getNombreUsuario());
    }

    // Test para obtener usuario por ID
    @Test
    void testGetUsuarioById() {
        Usuario usuario = new Usuario(4L, "Luis", "luis@mail.com", "clave1234", "999888777", EstadoUsuario.ACTIVO);

        when(usuarioRepository.findById(4L)).thenReturn(Optional.of(usuario));

        Usuario encontrado = usuarioService.getUsuario(4L);

        assertNotNull(encontrado);
        assertEquals("Luis", encontrado.getNombreUsuario());
    }

    // Test para usuario no encontrado
    @Test
    void testGetUsuarioNotFound() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UsuarioNotFoundException.class, () -> usuarioService.getUsuario(99L));
    }

    // Test para actualizar usuario
    @Test
    void testUpdateUsuario() {
        Usuario original = new Usuario(5L, "Maria", "maria@mail.com", "pass", "555444333", EstadoUsuario.ACTIVO);
        Usuario actualizado = new Usuario(5L, "Maria Modificada", "maria2024@mail.com", "newpass", "555444333", EstadoUsuario.INACTIVO);

        when(usuarioRepository.findById(5L)).thenReturn(Optional.of(original));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(actualizado);

        Usuario result = usuarioService.updateUsuario(5L, actualizado);

        assertEquals("Maria Modificada", result.getNombreUsuario());
        assertEquals("maria2024@mail.com", result.getCorreo());
    }

    // Test para eliminar usuario
    @Test
    void testDeleteUsuario() {
        when(usuarioRepository.existsById(6L)).thenReturn(true);

        usuarioService.deleteUsuario(6L);

        verify(usuarioRepository, times(1)).deleteById(6L);
    }

    // Test para eliminar usuario no encontrado
    @Test
    void testDeleteUsuarioNotFound() {
        when(usuarioRepository.existsById(100L)).thenReturn(false);

        assertThrows(UsuarioNotFoundException.class, () -> usuarioService.deleteUsuario(100L));
    }
}