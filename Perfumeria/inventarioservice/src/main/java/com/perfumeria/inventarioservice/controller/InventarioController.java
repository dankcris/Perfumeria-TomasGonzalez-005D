package com.perfumeria.inventarioservice.controller;

import com.perfumeria.inventarioservice.model.Inventario;
import com.perfumeria.inventarioservice.service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventario")
public class InventarioController {

    @Autowired
    private InventarioService service;

    @GetMapping
    public ResponseEntity<List<Inventario>> listar(){

        return ResponseEntity.ok(service.listar());
    }

    @PostMapping
    public ResponseEntity<Inventario> guardar(
            @RequestBody Inventario inventario){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.guardar(inventario));
    }
}