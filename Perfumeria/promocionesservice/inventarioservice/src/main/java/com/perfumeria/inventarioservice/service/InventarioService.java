package com.perfumeria.inventarioservice.service;
import com.perfumeria.inventarioservice.model.Inventario;
import com.perfumeria.inventarioservice.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class InventarioService {
    @Autowired private InventarioRepository repository;
    public List<Inventario> listar(){ return repository.findAll(); }
    public Inventario buscarPorId(Long id){
        return repository.findById(id).orElseThrow(()->new RuntimeException("Ítem de inventario no encontrado con id: "+id));
    }
    public Inventario guardar(Inventario inventario){ return repository.save(inventario); }
    public Inventario actualizar(Long id, Inventario inventario){
        Inventario existente = buscarPorId(id);
        existente.setNombrePerfume(inventario.getNombrePerfume());
        existente.setStock(inventario.getStock());
        return repository.save(existente);
    }
    public void eliminar(Long id){
        if(!repository.existsById(id)) throw new RuntimeException("Ítem de inventario no encontrado con id: "+id);
        repository.deleteById(id);
    }
}
