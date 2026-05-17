package com.perfumeria.inventarioservice.service;

import com.perfumeria.inventarioservice.model.Inventario;
import com.perfumeria.inventarioservice.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventarioService {

    @Autowired
    private InventarioRepository repository;

    public List<Inventario> listar(){

        return repository.findAll();
    }

    public Inventario guardar(Inventario inventario){

        return repository.save(inventario);
    }
}
