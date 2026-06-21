package com.perfumeria.clientesservice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.perfumeria.clientesservice.dto.ClienteDTO;
import com.perfumeria.clientesservice.model.Cliente;
import com.perfumeria.clientesservice.repository.ClienteRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias - ClienteService")
class ClienteServiceTest {

    @Mock
    private ClienteRepository repository;

    @InjectMocks
    private ClienteService service;

    @Test
    @DisplayName("Debe retornar la lista completa de clientes")
    void testListar() {
        // Given
        Cliente c1 = new Cliente(1L, "Ana Pérez", "ana@mail.com", "912345678");
        Cliente c2 = new Cliente(2L, "Luis Rojas", "luis@mail.com", "987654321");
        when(repository.findAll()).thenReturn(Arrays.asList(c1, c2));

        // When
        List<Cliente> resultado = service.listar();

        // Then
        assertEquals(2, resultado.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe retornar un cliente al buscar por id existente")
    void testBuscarPorIdExistente() {
        // Given
        Cliente cliente = new Cliente(1L, "Ana Pérez", "ana@mail.com", "912345678");
        when(repository.findById(1L)).thenReturn(Optional.of(cliente));

        // When
        Cliente resultado = service.buscarPorId(1L);

        // Then
        assertNotNull(resultado);
        assertEquals("Ana Pérez", resultado.getNombre());
        assertEquals("ana@mail.com", resultado.getCorreo());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debe lanzar excepción al buscar cliente con id inexistente")
    void testBuscarPorIdNoExiste() {
        // Given
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // When / Then
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.buscarPorId(99L));
        assertTrue(ex.getMessage().contains("99"));
        verify(repository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Debe guardar un nuevo cliente correctamente")
    void testGuardar() {
        // Given
        ClienteDTO dto = new ClienteDTO();
        dto.setNombre("María López");
        dto.setCorreo("maria@mail.com");
        dto.setTelefono("911223344");

        Cliente guardado = new Cliente(1L, "María López", "maria@mail.com", "911223344");
        when(repository.save(any(Cliente.class))).thenReturn(guardado);

        // When
        Cliente resultado = service.guardar(dto);

        // Then
        assertNotNull(resultado);
        assertEquals("María López", resultado.getNombre());
        assertEquals("maria@mail.com", resultado.getCorreo());
        verify(repository, times(1)).save(any(Cliente.class));
    }

    @Test
    @DisplayName("Debe actualizar un cliente existente correctamente")
    void testActualizar() {
        // Given
        Cliente existente = new Cliente(1L, "Ana Pérez", "ana@mail.com", "912345678");
        ClienteDTO dto = new ClienteDTO();
        dto.setNombre("Ana González");
        dto.setCorreo("ana.g@mail.com");
        dto.setTelefono("999888777");

        Cliente actualizado = new Cliente(1L, "Ana González", "ana.g@mail.com", "999888777");
        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(repository.save(any(Cliente.class))).thenReturn(actualizado);

        // When
        Cliente resultado = service.actualizar(1L, dto);

        // Then
        assertNotNull(resultado);
        assertEquals("Ana González", resultado.getNombre());
        assertEquals("ana.g@mail.com", resultado.getCorreo());
        verify(repository, times(1)).save(any(Cliente.class));
    }

    @Test
    @DisplayName("Debe eliminar un cliente existente correctamente")
    void testEliminarExitoso() {
        // Given
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        // When
        assertDoesNotThrow(() -> service.eliminar(1L));

        // Then
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Debe lanzar excepción al eliminar cliente con id inexistente")
    void testEliminarNoExiste() {
        // Given
        when(repository.existsById(99L)).thenReturn(false);

        // When / Then
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.eliminar(99L));
        assertTrue(ex.getMessage().contains("99"));
        verify(repository, never()).deleteById(anyLong());
    }
}
