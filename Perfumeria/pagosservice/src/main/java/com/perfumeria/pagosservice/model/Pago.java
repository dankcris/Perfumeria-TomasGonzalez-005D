package com.perfumeria.pagosservice.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pagos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long ventaId;

    @Column(nullable = false)
    private String metodoPago;

    @Column(nullable = false)
    private Double monto;

    @Column(nullable = false)
    private String estado; // PENDIENTE, APROBADO, RECHAZADO

    private LocalDateTime fechaPago;
}
