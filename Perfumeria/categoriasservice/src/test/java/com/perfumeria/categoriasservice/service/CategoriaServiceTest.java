package com.perfumeria.categoriasservice.service;

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

import com.perfumeria.categoriasservice.dto.CategoriaDTO;
import com.perfumeria.categoriasservice.model.Categoria;
import com.perfumeria.categoriasservice.repository.CategoriaRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias - CategoriaService")
class CategoriaServiceTest {

    @Mock
    private CategoriaRepository repository;

    @InjectMocks
    private CategoriaService service;

    @Test
    @DisplayName("Debe retornar la lista completa de categorías")
    void testListar() {
        // Given
        Categoria c1 = new Categoria(1L, "Floral", "Perfumes florales");
        Categoria c2 = new Categoria(2L, "Amaderado", "Perfumes amaderados");
        when(repository.findAll()).thenReturn(Arrays.asList(c1, c2));
        // When
        List<Categoria> resultado = service.listar();
        // Then
        assertEquals(2, resultado.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe guardar una categoría correctamente cuando el nombre no existe")
    void testGuardarCategoriaExitoso() {
        // Given
        CategoriaDTO dto = new CategoriaDTO();
        dto.setNombre("Oriental");
        dto.setDescripcion("Perfumes con notas orientales");
        Categoria guardada = new Categoria(1L, "Oriental", "Perfumes con notas orientales");
        when(repository.existsByNombre("Oriental")).thenReturn(false);
        when(repository.save(any(Categoria.class))).thenReturn(guardada);
        // When
        Categoria resultado = service.guardar(dto);
        // Then
        assertNotNull(resultado);
        assertEquals("Oriental", resultado.getNombre());
        verify(repository, times(1)).save(any(Categoria.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción si la categoría ya existe")
    void testGuardarCategoriaYaExiste() {
        // Given
        CategoriaDTO dto = new CategoriaDTO();
        dto.setNombre("Floral");
        when(repository.existsByNombre("Floral")).thenReturn(true);
        // When / Then
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.guardar(dto));
        assertEquals("Ya existe una categoría con ese nombre.", ex.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Debe lanzar excepción al buscar categoría con id inexistente")
    void testObtenerPorIdNoExiste() {
        // Given
        when(repository.findById(99L)).thenReturn(Optional.empty());
        // When / Then
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.obtenerPorId(99L));
        assertTrue(ex.getMessage().contains("Categoría no encontrada"));
    }

    @Test
    @DisplayName("Debe eliminar una categoría existente correctamente")
    void testEliminarExitoso() {
        // Given
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);
        // When
        assertDoesNotThrow(() -> service.eliminar(1L));
        // Then
        verify(repository, times(1)).deleteById(1L);
    }
}
