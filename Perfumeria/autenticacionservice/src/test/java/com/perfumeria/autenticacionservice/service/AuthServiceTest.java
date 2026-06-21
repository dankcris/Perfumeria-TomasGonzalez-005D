package com.perfumeria.autenticacionservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.perfumeria.autenticacionservice.dto.AuthRequestDTO;
import com.perfumeria.autenticacionservice.model.Rol;
import com.perfumeria.autenticacionservice.model.Usuario;
import com.perfumeria.autenticacionservice.repository.RolRepository;
import com.perfumeria.autenticacionservice.repository.UsuarioRepository;

/**
 * Pruebas unitarias de la capa Service del microservicio de autenticación.
 * Se aplica la estructura AAA (Arrange, Act, Assert) y se aíslan las dependencias
 * (UsuarioRepository, RolRepository, PasswordEncoder, JwtService) con Mockito,
 * tal como se indica en el material de "Pruebas Unitarias en Proyectos de Microservicios".
 */
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RolRepository rolRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    @Test
    @DisplayName("Debería registrar un usuario nuevo asignando el rol CLIENTE por defecto")
    void registrar_DeberiaAsignarRolClientePorDefecto() {

        // Given (Arrange)
        AuthRequestDTO request = new AuthRequestDTO("ana123", "clave123", "ana@correo.com", null);
        Rol rolCliente = new Rol(1L, "CLIENTE");

        when(usuarioRepository.findByNombreUsuario("ana123")).thenReturn(Optional.empty());
        when(rolRepository.findByNombreRol("CLIENTE")).thenReturn(Optional.of(rolCliente));
        when(passwordEncoder.encode("clave123")).thenReturn("claveEncriptada");

        // When (Act)
        String resultado = authService.registrar(request);

        // Then (Assert)
        assertEquals("Usuario registrado correctamente", resultado);
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Debería lanzar una excepción si el nombre de usuario ya existe")
    void registrar_DeberiaLanzarExcepcionSiUsuarioYaExiste() {

        // Given
        AuthRequestDTO request = new AuthRequestDTO("ana123", "clave123", "ana@correo.com", null);

        when(usuarioRepository.findByNombreUsuario("ana123"))
                .thenReturn(Optional.of(new Usuario()));

        // When / Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> authService.registrar(request));

        assertEquals("El nombre de usuario ya existe.", exception.getMessage());
        verify(usuarioRepository, times(0)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Debería generar un token cuando las credenciales son correctas")
    void login_DeberiaGenerarTokenConCredencialesValidas() {

        // Given
        Rol rolVendedor = new Rol(2L, "VENDEDOR");
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombreUsuario("vendedor1");
        usuario.setContrasena("claveEncriptada");
        usuario.setRoles(Set.of(rolVendedor));

        when(usuarioRepository.findByNombreUsuario("vendedor1")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("clave123", "claveEncriptada")).thenReturn(true);
        when(jwtService.generarToken("vendedor1", List.of("VENDEDOR"))).thenReturn("token.simulado.jwt");

        // When
        String token = authService.login("vendedor1", "clave123");

        // Then
        assertNotNull(token);
        assertEquals("token.simulado.jwt", token);
        verify(jwtService, times(1)).generarToken("vendedor1", List.of("VENDEDOR"));
    }

    @Test
    @DisplayName("Debería lanzar una excepción cuando la contraseña es incorrecta")
    void login_DeberiaLanzarExcepcionConContrasenaIncorrecta() {

        // Given
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario("vendedor1");
        usuario.setContrasena("claveEncriptada");

        when(usuarioRepository.findByNombreUsuario("vendedor1")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("claveIncorrecta", "claveEncriptada")).thenReturn(false);

        // When / Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> authService.login("vendedor1", "claveIncorrecta"));

        assertEquals("Credenciales inválidas", exception.getMessage());
    }
}
