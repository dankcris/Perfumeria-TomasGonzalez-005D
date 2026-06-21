package com.perfumeria.autenticacionservice.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO utilizado tanto para el registro como para el login.
 * En login solo se usan nombreUsuario y contrasena; correo y roles se ignoran.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequestDTO {

    private String nombreUsuario;

    private String contrasena;

    private String correo;

    // Roles deseados al registrarse, ej: ["ADMIN"], ["VENDEDOR"], ["CLIENTE"]
    private Set<String> roles;
}
