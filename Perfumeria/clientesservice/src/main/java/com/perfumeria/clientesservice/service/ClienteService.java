package com.perfumeria.clientesservice.service;
import com.perfumeria.clientesservice.dto.ClienteDTO;
import com.perfumeria.clientesservice.model.Cliente;
import com.perfumeria.clientesservice.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class ClienteService {
    @Autowired private ClienteRepository repository;
    public List<Cliente> listar(){ return repository.findAll(); }
    public Cliente buscarPorId(Long id){
        return repository.findById(id).orElseThrow(()->new RuntimeException("Cliente no encontrado con id: "+id));
    }
    public Cliente guardar(ClienteDTO dto){
        Cliente cliente = new Cliente();
        cliente.setNombre(dto.getNombre());
        cliente.setCorreo(dto.getCorreo());
        cliente.setTelefono(dto.getTelefono());
        return repository.save(cliente);
    }
    public Cliente actualizar(Long id, ClienteDTO dto){
        Cliente existente = buscarPorId(id);
        existente.setNombre(dto.getNombre());
        existente.setCorreo(dto.getCorreo());
        existente.setTelefono(dto.getTelefono());
        return repository.save(existente);
    }
    public void eliminar(Long id){
        if(!repository.existsById(id)) throw new RuntimeException("Cliente no encontrado con id: "+id);
        repository.deleteById(id);
    }
}
