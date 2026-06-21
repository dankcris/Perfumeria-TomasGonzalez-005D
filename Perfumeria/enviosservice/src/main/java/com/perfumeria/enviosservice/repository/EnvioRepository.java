package com.perfumeria.enviosservice.repository;

import java.util.List;

import com.perfumeria.enviosservice.model.Envio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnvioRepository extends JpaRepository<Envio, Long> {
    List<Envio> findByVentaId(Long ventaId);
    List<Envio> findByEstado(String estado);
}
