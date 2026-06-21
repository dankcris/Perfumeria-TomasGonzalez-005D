package com.perfumeria.perfumesservice.service;

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

import com.perfumeria.perfumesservice.model.Perfume;
import com.perfumeria.perfumesservice.repository.PerfumeRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias - PerfumeService")
class PerfumeServiceTest {

    @Mock
    private PerfumeRepository repository;

    @InjectMocks
    private PerfumeService service;

    @Test
    @DisplayName("Debe retornar la lista completa de perfumes")
    void testListar() {
        // Given
        Perfume p1 = new Perfume(1L, "Chanel N°5", "Chanel", 89990.0, 20);
        Perfume p2 = new Perfume(2L, "Acqua di Gio", "Giorgio Armani", 75000.0, 15);
        when(repository.findAll()).thenReturn(Arrays.asList(p1, p2));

        // When
        List<Perfume> resultado = service.listar();

        // Then
        assertEquals(2, resultado.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe retornar un perfume al buscar por id existente")
    void testBuscarExistente() {
        // Given
        Perfume perfume = new Perfume(1L, "Chanel N°5", "Chanel", 89990.0, 20);
        when(repository.findById(1L)).thenReturn(Optional.of(perfume));

        // When
        Perfume resultado = service.buscar(1L);

        // Then
        assertNotNull(resultado);
        assertEquals("Chanel N°5", resultado.getNombre());
        assertEquals("Chanel", resultado.getMarca());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debe lanzar excepción al buscar perfume con id inexistente")
    void testBuscarNoExiste() {
        // Given
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // When / Then
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.buscar(99L));
        assertTrue(ex.getMessage().contains("99"));
        verify(repository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Debe guardar un nuevo perfume correctamente")
    void testGuardar() {
        // Given
        Perfume perfume = new Perfume(null, "Dior Sauvage", "Dior", 95000.0, 10);
        Perfume guardado = new Perfume(1L, "Dior Sauvage", "Dior", 95000.0, 10);
        when(repository.save(any(Perfume.class))).thenReturn(guardado);

        // When
        Perfume resultado = service.guardar(perfume);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Dior Sauvage", resultado.getNombre());
        verify(repository, times(1)).save(perfume);
    }

    @Test
    @DisplayName("Debe actualizar un perfume existente correctamente")
    void testActualizar() {
        // Given
        Perfume existente = new Perfume(1L, "Chanel N°5", "Chanel", 89990.0, 20);
        Perfume datos = new Perfume(null, "Chanel N°5 Edición Limitada", "Chanel", 99990.0, 5);
        Perfume actualizado = new Perfume(1L, "Chanel N°5 Edición Limitada", "Chanel", 99990.0, 5);

        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(repository.save(any(Perfume.class))).thenReturn(actualizado);

        // When
        Perfume resultado = service.actualizar(1L, datos);

        // Then
        assertNotNull(resultado);
        assertEquals("Chanel N°5 Edición Limitada", resultado.getNombre());
        assertEquals(99990.0, resultado.getPrecio());
        verify(repository, times(1)).save(any(Perfume.class));
    }

    @Test
    @DisplayName("Debe eliminar un perfume existente correctamente")
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
    @DisplayName("Debe lanzar excepción al eliminar perfume con id inexistente")
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
