package com.perfumeria.perfumesservice.crontoller;

import com.perfumeria.perfumesservice.model.Perfume;
import com.perfumeria.perfumesservice.service.PerfumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/perfumes")
@RequiredArgsConstructor
public class PerfumeController {

    private final PerfumeService service;

    @GetMapping
    public ResponseEntity<List<Perfume>> listar(){

        return ResponseEntity.ok(service.listar());
    }

    @PostMapping
    public ResponseEntity<Perfume> guardar(
            @RequestBody Perfume perfume){

        return ResponseEntity.ok(service.guardar(perfume));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Perfume> buscar(@PathVariable Long id){

        return ResponseEntity.ok(service.buscar(id));
    }
}
