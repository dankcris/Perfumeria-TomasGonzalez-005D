package com.perfumeria.promocionesservice.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "promociones")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Promocion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la promoción es obligatorio")
    @Column(nullable = false)
    private String nombre;

    private String descripcion;

    @NotNull(message = "El porcentaje de descuento es obligatorio")
    @DecimalMin(value = "0.01", message = "El descuento debe ser mayor a 0")
    @DecimalMax(value = "100.0", message = "El descuento no puede superar el 100%")
    @Column(nullable = false)
    private Double descuentoPorcentaje;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de fin es obligatoria")
    private LocalDate fechaFin;

    private boolean activa;
}
