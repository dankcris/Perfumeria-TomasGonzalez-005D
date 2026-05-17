package com.perfumeria.ventasservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ventas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clienteId;

    private Long perfumeId;

    private int cantidad;

    private double total;
}