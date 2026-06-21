package com.perfumeria.pagosservice.repository;

import java.util.List;

import com.perfumeria.pagosservice.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
    List<Pago> findByVentaId(Long ventaId);
}
