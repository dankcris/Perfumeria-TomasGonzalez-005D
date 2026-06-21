package com.perfumeria.promocionesservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfumeria.promocionesservice.dto.PromocionDTO;
import com.perfumeria.promocionesservice.model.Promocion;
import com.perfumeria.promocionesservice.repository.PromocionRepository;

@Service
public class PromocionService {

    @Autowired
    private PromocionRepository repository;

    public List<Promocion> listar() {
        return repository.findAll();
    }

    public List<Promocion> listarActivas() {
        return repository.findByActivaTrue();
    }

    public Promocion obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promoción no encontrada con id: " + id));
    }

    public Promocion guardar(PromocionDTO dto) {
        if (dto.getFechaFin().isBefore(dto.getFechaInicio())) {
            throw new RuntimeException("La fecha de fin no puede ser anterior a la fecha de inicio.");
        }
        Promocion p = new Promocion();
        p.setNombre(dto.getNombre());
        p.setDescripcion(dto.getDescripcion());
        p.setDescuentoPorcentaje(dto.getDescuentoPorcentaje());
        p.setFechaInicio(dto.getFechaInicio());
        p.setFechaFin(dto.getFechaFin());
        p.setActiva(dto.isActiva());
        return repository.save(p);
    }

    public Promocion actualizar(Long id, PromocionDTO dto) {
        Promocion existente = obtenerPorId(id);
        existente.setNombre(dto.getNombre());
        existente.setDescripcion(dto.getDescripcion());
        existente.setDescuentoPorcentaje(dto.getDescuentoPorcentaje());
        existente.setFechaInicio(dto.getFechaInicio());
        existente.setFechaFin(dto.getFechaFin());
        existente.setActiva(dto.isActiva());
        return repository.save(existente);
    }

    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Promoción no encontrada con id: " + id);
        }
        repository.deleteById(id);
    }
}
