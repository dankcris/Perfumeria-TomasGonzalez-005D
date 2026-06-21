package com.perfumeria.inventarioservice.repository;

import com.perfumeria.inventarioservice.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {

}