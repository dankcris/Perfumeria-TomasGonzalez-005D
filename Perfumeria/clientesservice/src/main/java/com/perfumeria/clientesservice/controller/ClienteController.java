package com.perfumeria.clientesservice.controller;

import com.perfumeria.clientesservice.dto.ClienteDTO;
import com.perfumeria.clientesservice.model.Cliente;
import com.perfumeria.clientesservice.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @GetMapping
    public ResponseEntity<List<Cliente>> listar() {

        return ResponseEntity.ok(service.listar());
    }

    @PostMapping
    public ResponseEntity<Cliente> guardar(
            @RequestBody ClienteDTO dto){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.guardar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id){

        try {

            Cliente cliente = service.buscarPorId(id);

            return ResponseEntity.ok(cliente);

        } catch (RuntimeException e){

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){

        try {

            service.eliminar(id);

            return ResponseEntity.noContent().build();

        } catch (RuntimeException e){

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}