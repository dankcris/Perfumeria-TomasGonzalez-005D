package com.perfumeria.autenticacionservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perfumeria.autenticacionservice.dto.AuthRequestDTO;
import com.perfumeria.autenticacionservice.dto.AuthResponseDTO;
import com.perfumeria.autenticacionservice.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticación", description = "Registro e inicio de sesión de usuarios. Genera el token JWT que se debe enviar en el header Authorization de las demás peticiones (Bearer {token}).")
public class AutenticacionController {

    @Autowired
    private AuthService authService;

    @Operation(summary = "Registrar un nuevo usuario", description = "Crea un usuario con uno o más roles (ADMIN, VENDEDOR, CLIENTE). Si no se envían roles, se asigna CLIENTE por defecto.")
    @PostMapping("/registrar")
    public ResponseEntity<String> registrar(@RequestBody AuthRequestDTO request) {

        try {

            return ResponseEntity.ok(authService.registrar(request));

        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Iniciar sesión", description = "Valida credenciales y retorna un token JWT (válido 24 horas) con los roles del usuario embebidos.")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDTO request) {

        try {

            String token = authService.login(request.getNombreUsuario(), request.getContrasena());

            return ResponseEntity.ok(new AuthResponseDTO(token));

        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
