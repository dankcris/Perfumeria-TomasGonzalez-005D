package com.perfumeria.proveedoresservice.service;

import com.perfumeria.proveedoresservice.model.Proveedor;
import com.perfumeria.proveedoresservice.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository repository;

    public List<Proveedor> listar(){

        return repository.findAll();
    }

    public Proveedor guardar(Proveedor proveedor){

        return repository.save(proveedor);
    }
}