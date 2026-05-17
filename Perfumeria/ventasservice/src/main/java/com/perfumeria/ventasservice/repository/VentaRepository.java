package com.perfumeria.ventasservice.repository;

import com.perfumeria.ventasservice.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaRepository extends JpaRepository<Venta, Long> {

}