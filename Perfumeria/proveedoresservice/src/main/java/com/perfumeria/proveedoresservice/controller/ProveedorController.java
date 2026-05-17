package com.perfumeria.proveedoresservice.controller;

import com.perfumeria.proveedoresservice.model.Proveedor;
import com.perfumeria.proveedoresservice.service.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService service;

    @GetMapping
    public ResponseEntity<List<Proveedor>> listar(){

        return ResponseEntity.ok(service.listar());
    }

    @PostMapping
    public ResponseEntity<Proveedor> guardar(
            @RequestBody Proveedor proveedor){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.guardar(proveedor));
    }
}
