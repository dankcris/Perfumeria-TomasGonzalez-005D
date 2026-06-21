package com.perfumeria.perfumesservice.service;
import com.perfumeria.perfumesservice.model.Perfume;
import com.perfumeria.perfumesservice.repository.PerfumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class PerfumeService {
    @Autowired private PerfumeRepository repository;
    public List<Perfume> listar(){ return repository.findAll(); }
    public Perfume buscar(Long id){
        return repository.findById(id).orElseThrow(()->new RuntimeException("Perfume no encontrado con id: "+id));
    }
    public Perfume guardar(Perfume perfume){ return repository.save(perfume); }
    public Perfume actualizar(Long id, Perfume perfume){
        Perfume existente = buscar(id);
        existente.setNombre(perfume.getNombre());
        existente.setMarca(perfume.getMarca());
        existente.setPrecio(perfume.getPrecio());
        existente.setStock(perfume.getStock());
        return repository.save(existente);
    }
    public void eliminar(Long id){
        if(!repository.existsById(id)) throw new RuntimeException("Perfume no encontrado con id: "+id);
        repository.deleteById(id);
    }
}
