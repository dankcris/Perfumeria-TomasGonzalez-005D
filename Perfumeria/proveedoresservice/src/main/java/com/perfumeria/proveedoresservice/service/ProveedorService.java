package com.perfumeria.proveedoresservice.service;
import com.perfumeria.proveedoresservice.model.Proveedor;
import com.perfumeria.proveedoresservice.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class ProveedorService {
    @Autowired private ProveedorRepository repository;
    public List<Proveedor> listar(){ return repository.findAll(); }
    public Proveedor buscarPorId(Long id){
        return repository.findById(id).orElseThrow(()->new RuntimeException("Proveedor no encontrado con id: "+id));
    }
    public Proveedor guardar(Proveedor proveedor){ return repository.save(proveedor); }
    public Proveedor actualizar(Long id, Proveedor proveedor){
        Proveedor existente = buscarPorId(id);
        existente.setNombre(proveedor.getNombre());
        existente.setEmpresa(proveedor.getEmpresa());
        existente.setTelefono(proveedor.getTelefono());
        return repository.save(existente);
    }
    public void eliminar(Long id){
        if(!repository.existsById(id)) throw new RuntimeException("Proveedor no encontrado con id: "+id);
        repository.deleteById(id);
    }
}
