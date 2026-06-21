package com.perfumeria.enviosservice.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "envios")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long ventaId;

    @Column(nullable = false)
    private String direccionDestino;

    @Column(nullable = false)
    private String ciudadDestino;

    @Column(nullable = false)
    private String transportista;

    @Column(nullable = false)
    private String estado; // PENDIENTE, EN_CAMINO, ENTREGADO

    private LocalDateTime fechaEnvio;

    private String numeroSeguimiento;
}
