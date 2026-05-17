package com.perfumeria.perfumesservice.service;

import com.perfumeria.perfumesservice.model.Perfume;
import com.perfumeria.perfumesservice.repository.PerfumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerfumeService {

    @Autowired
    private PerfumeRepository repository;

    public List<Perfume> listar(){

        return repository.findAll();
    }

    public Perfume guardar(Perfume perfume){

        return repository.save(perfume);
    }

    public Perfume buscar(Long id){

        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfume no encontrado"));
    }
}