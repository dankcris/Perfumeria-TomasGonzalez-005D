package com.perfumeria.perfumesservice.repository;

import com.perfumeria.perfumesservice.model.Perfume;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfumeRepository extends JpaRepository<Perfume, Long> {
}
