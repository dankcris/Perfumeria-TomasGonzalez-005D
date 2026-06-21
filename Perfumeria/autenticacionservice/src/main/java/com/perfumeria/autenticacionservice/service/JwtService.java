package com.perfumeria.autenticacionservice.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

/**
 * Servicio encargado exclusivamente de generar tokens JWT.
 * La clave secreta se inyecta desde application.properties (jwt.secret)
 * y debe ser EXACTAMENTE la misma que se configura en el api-gateway,
 * ya que el gateway es quien valida el token en cada petición.
 */
@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secreto;

    // 1000ms * 60s * 60m * 24h = 24 horas de vigencia
    private static final long VIGENCIA_MS = 1000L * 60 * 60 * 24;

    public String generarToken(String nombreUsuario, List<String> roles) {

        Date ahora = new Date();
        Date expiracion = new Date(ahora.getTime() + VIGENCIA_MS);

        return Jwts.builder()
                .setSubject(nombreUsuario)
                .claim("roles", roles)
                .setIssuedAt(ahora)
                .setExpiration(expiracion)
                .signWith(Keys.hmacShaKeyFor(secreto.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .compact();
    }
}
