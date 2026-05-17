package com.perfumeria.perfumesservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "perfumes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Perfume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String marca;

    private double precio;

    private int stock;
}