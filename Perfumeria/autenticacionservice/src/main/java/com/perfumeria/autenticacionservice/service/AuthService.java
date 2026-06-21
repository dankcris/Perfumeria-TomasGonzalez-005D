package com.perfumeria.autenticacionservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.perfumeria.autenticacionservice.dto.AuthRequestDTO;
import com.perfumeria.autenticacionservice.model.Rol;
import com.perfumeria.autenticacionservice.model.Usuario;
import com.perfumeria.autenticacionservice.repository.RolRepository;
import com.perfumeria.autenticacionservice.repository.UsuarioRepository;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    /**
     * Registra un nuevo usuario. Si no se especifican roles, se asigna CLIENTE por defecto.
     */
    public String registrar(AuthRequestDTO request) {

        if (usuarioRepository.findByNombreUsuario(request.getNombreUsuario()).isPresent()) {
            throw new RuntimeException("El nombre de usuario ya existe.");
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombreUsuario(request.getNombreUsuario());
        nuevoUsuario.setCorreo(request.getCorreo());
        nuevoUsuario.setContrasena(passwordEncoder.encode(request.getContrasena()));

        if (request.getRoles() == null || request.getRoles().isEmpty()) {

            Rol rolPorDefecto = rolRepository.findByNombreRol("CLIENTE")
                    .orElseThrow(() -> new RuntimeException("Error: el rol CLIENTE no existe en la base de datos."));

            nuevoUsuario.agregarRol(rolPorDefecto);

        } else {

            for (String nombreRol : request.getRoles()) {

                Rol rolEncontrado = rolRepository.findByNombreRol(nombreRol.toUpperCase())
                        .orElseThrow(() -> new RuntimeException("Error: el rol " + nombreRol + " no existe en la base de datos."));

                nuevoUsuario.agregarRol(rolEncontrado);
            }
        }

        usuarioRepository.save(nuevoUsuario);

        return "Usuario registrado correctamente";
    }

    /**
     * Valida credenciales y genera el token JWT con los roles del usuario.
     */
    @Transactional(readOnly = true)
    public String login(String nombreUsuario, String contrasena) {

        Usuario usuario = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

        if (!passwordEncoder.matches(contrasena, usuario.getContrasena())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        List<String> roles = usuario.getRoles().stream()
                .map(Rol::getNombreRol)
                .collect(Collectors.toList());

        return jwtService.generarToken(usuario.getNombreUsuario(), roles);
    }
}
