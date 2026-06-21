package com.perfumeria.ventasservice.service;

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

import com.perfumeria.ventasservice.client.ClientCliente;
import com.perfumeria.ventasservice.client.ClientPerfume;
import com.perfumeria.ventasservice.dto.VentaDTO;
import com.perfumeria.ventasservice.model.Cliente;
import com.perfumeria.ventasservice.model.Perfume;
import com.perfumeria.ventasservice.model.Venta;
import com.perfumeria.ventasservice.repository.VentaRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias - VentaService")
class VentaServiceTest {

    @Mock
    private VentaRepository repository;

    @Mock
    private ClientCliente clientCliente;

    @Mock
    private ClientPerfume clientPerfume;

    @InjectMocks
    private VentaService service;

    private Cliente buildCliente(Long id) {
        Cliente c = new Cliente();
        c.setId(id);
        c.setNombre("Ana Pérez");
        c.setCorreo("ana@mail.com");
        c.setTelefono("912345678");
        return c;
    }

    private Perfume buildPerfume(Long id, double precio) {
        Perfume p = new Perfume();
        p.setId(id);
        p.setNombre("Chanel N°5");
        p.setMarca("Chanel");
        p.setPrecio(precio);
        p.setStock(10);
        return p;
    }

    @Test
    @DisplayName("Debe registrar una nueva venta correctamente calculando el total")
    void testGuardar() {
        // Given
        VentaDTO dto = new VentaDTO();
        dto.setClienteId(1L);
        dto.setPerfumeId(2L);
        dto.setCantidad(3);

        Cliente cliente = buildCliente(1L);
        Perfume perfume = buildPerfume(2L, 50000.0);

        Venta ventaGuardada = new Venta(10L, 1L, 2L, 3, 150000.0);

        when(clientCliente.obtenerCliente(1L)).thenReturn(cliente);
        when(clientPerfume.obtenerPerfume(2L)).thenReturn(perfume);
        when(repository.save(any(Venta.class))).thenReturn(ventaGuardada);

        // When
        Venta resultado = service.guardar(dto);

        // Then
        assertNotNull(resultado);
        assertEquals(150000.0, resultado.getTotal());
        assertEquals(1L, resultado.getClienteId());
        assertEquals(2L, resultado.getPerfumeId());
        verify(repository, times(1)).save(any(Venta.class));
    }

    @Test
    @DisplayName("Debe retornar la lista completa de ventas")
    void testListar() {
        // Given
        Venta v1 = new Venta(1L, 1L, 2L, 2, 100000.0);
        Venta v2 = new Venta(2L, 3L, 1L, 1, 75000.0);
        when(repository.findAll()).thenReturn(Arrays.asList(v1, v2));

        // When
        List<Venta> resultado = service.listar();

        // Then
        assertEquals(2, resultado.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe retornar una venta al buscar por id existente")
    void testObtenerPorIdExistente() {
        // Given
        Venta venta = new Venta(1L, 1L, 2L, 2, 100000.0);
        when(repository.findById(1L)).thenReturn(Optional.of(venta));

        // When
        Venta resultado = service.obtenerPorId(1L);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(100000.0, resultado.getTotal());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debe lanzar excepción al buscar venta con id inexistente")
    void testObtenerPorIdNoExiste() {
        // Given
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // When / Then
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.obtenerPorId(99L));
        assertTrue(ex.getMessage().contains("99"));
        verify(repository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Debe actualizar una venta existente recalculando el total")
    void testActualizar() {
        // Given
        Venta existente = new Venta(1L, 1L, 2L, 2, 100000.0);
        VentaDTO dto = new VentaDTO();
        dto.setClienteId(1L);
        dto.setPerfumeId(2L);
        dto.setCantidad(5);

        Cliente cliente = buildCliente(1L);
        Perfume perfume = buildPerfume(2L, 50000.0);
        Venta actualizada = new Venta(1L, 1L, 2L, 5, 250000.0);

        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(clientCliente.obtenerCliente(1L)).thenReturn(cliente);
        when(clientPerfume.obtenerPerfume(2L)).thenReturn(perfume);
        when(repository.save(any(Venta.class))).thenReturn(actualizada);

        // When
        Venta resultado = service.actualizar(1L, dto);

        // Then
        assertNotNull(resultado);
        assertEquals(250000.0, resultado.getTotal());
        assertEquals(5, resultado.getCantidad());
        verify(repository, times(1)).save(any(Venta.class));
    }

    @Test
    @DisplayName("Debe eliminar una venta existente correctamente")
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
    @DisplayName("Debe lanzar excepción al eliminar venta con id inexistente")
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
