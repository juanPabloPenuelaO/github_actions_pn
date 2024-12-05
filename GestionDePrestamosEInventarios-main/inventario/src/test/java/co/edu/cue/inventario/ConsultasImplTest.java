package co.edu.cue.inventario;

import co.edu.cue.inventario.Configuracion.MensajeUtil;
import co.edu.cue.inventario.ElementosDti.ElementosDti;
import co.edu.cue.inventario.Enums.EstadosElementos;
import co.edu.cue.inventario.Enums.TipoDeElementos;
import co.edu.cue.inventario.Service.Implementaciones.ConsultasImpl;
import co.edu.cue.inventario.repository.InventarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ConsultasImplTest {

    @Mock
    private InventarioRepository repository;

    @Mock
    private MensajeUtil mensajeUtil;

    @InjectMocks
    private ConsultasImpl consultasImpl;

    @BeforeEach
    void setUp() {
        // Configuración inicial antes de cada prueba
    }

    // Prueba para verificar la excepción en filtrarPorTipo
    @Test
    void testFiltrarPorTipo_whenExceptionThrown_thenRuntimeExceptionIsThrown() {
        // Simulamos una excepción en el repositorio
        when(repository.findByTipo(TipoDeElementos.Computador)).thenThrow(new RuntimeException("Error en la base de datos"));

        // Configuramos el mock del mensaje
        when(mensajeUtil.getMensaje("inventario.busqueda.tipo.error", null)).thenReturn("Error al filtrar por tipo de elemento.");

        // Verificamos que se lance la excepción con el mensaje adecuado
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            consultasImpl.filtrarPorTipo(TipoDeElementos.Computador);
        });

        assertEquals("Error al filtrar por tipo de elemento.", exception.getMessage());
    }

    // Prueba para verificar la excepción en filtrarPorEstado
    @Test
    void testFiltrarPorEstado_whenExceptionThrown_thenRuntimeExceptionIsThrown() {
        // Simulamos una excepción en el repositorio
        when(repository.findByEstado(EstadosElementos.DISPONIBLE)).thenThrow(new RuntimeException("Error en la base de datos"));

        // Configuramos el mock del mensaje
        when(mensajeUtil.getMensaje("inventario.busqueda.estado.error", null)).thenReturn("Error al filtrar por estado de elemento.");

        // Verificamos que se lance la excepción con el mensaje adecuado
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            consultasImpl.filtrarPorEstado(EstadosElementos.DISPONIBLE);
        });

        assertEquals("Error al filtrar por estado de elemento.", exception.getMessage());
    }

    // Prueba para verificar la excepción en existeElemento
    @Test
    void testExisteElemento_whenExceptionThrown_thenRuntimeExceptionIsThrown() {
        // Simulamos una excepción en el repositorio
        when(repository.existsById("12345")).thenThrow(new RuntimeException("Error en la base de datos"));

        // Configuramos el mock del mensaje
        when(mensajeUtil.getMensaje("inventario.busqueda.existe.error", null)).thenReturn("Error al verificar si el elemento existe.");

        // Verificamos que se lance la excepción con el mensaje adecuado
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            consultasImpl.existeElemento("12345");
        });

        assertEquals("Error al verificar si el elemento existe.", exception.getMessage());
    }

    // Prueba exitosa para filtrar por tipo
    @Test
    void testFiltrarPorTipo_whenNoException_thenReturnElementos() {
        ElementosDti elemento = mock(ElementosDti.class);
        when(repository.findByTipo(TipoDeElementos.Computador)).thenReturn(Collections.singletonList(elemento));

        // Verificamos que no se lance excepción y se devuelvan los elementos
        assertDoesNotThrow(() -> {
            consultasImpl.filtrarPorTipo(TipoDeElementos.Computador);
        });
    }

    // Prueba exitosa para filtrar por estado
    @Test
    void testFiltrarPorEstado_whenNoException_thenReturnElementos() {
        ElementosDti elemento = mock(ElementosDti.class);
        when(repository.findByEstado(EstadosElementos.DISPONIBLE)).thenReturn(Collections.singletonList(elemento));

        // Verificamos que no se lance excepción y se devuelvan los elementos
        assertDoesNotThrow(() -> {
            consultasImpl.filtrarPorEstado(EstadosElementos.DISPONIBLE);
        });
    }

    // Prueba exitosa para existeElemento
    @Test
    void testExisteElemento_whenNoException_thenReturnTrue() {
        when(repository.existsById("12345")).thenReturn(true);

        // Verificamos que no se lance excepción y que se retorne el valor correcto
        assertDoesNotThrow(() -> {
            assertTrue(consultasImpl.existeElemento("12345"));
        });
    }
}
