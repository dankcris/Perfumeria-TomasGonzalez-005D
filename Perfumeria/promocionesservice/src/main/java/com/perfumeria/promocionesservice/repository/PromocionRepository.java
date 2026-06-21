package com.perfumeria.promocionesservice.repository;

import java.util.List;

import com.perfumeria.promocionesservice.model.Promocion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromocionRepository extends JpaRepository<Promocion, Long> {
    List<Promocion> findByActivaTrue();
}
