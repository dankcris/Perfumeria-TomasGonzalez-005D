package com.perfumeria.proveedoresservice.repository;

import com.perfumeria.proveedoresservice.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

}