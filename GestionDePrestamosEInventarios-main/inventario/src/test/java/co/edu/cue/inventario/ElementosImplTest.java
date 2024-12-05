package co.edu.cue.inventario;

import co.edu.cue.inventario.ElementosDti.ElementosDti;
import co.edu.cue.inventario.Enums.EstadosElementos;
import co.edu.cue.inventario.Enums.TipoDeElementos;
import co.edu.cue.inventario.Fabricas.ElementosDtiFabrica;
import co.edu.cue.inventario.Fabricas.SeleccionFabrica;
import co.edu.cue.inventario.Service.Implementaciones.ElementosImpl;
import co.edu.cue.inventario.repository.InventarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ElementosImplTest {

    @Mock
    private InventarioRepository repository;  // Mock del repositorio de inventario

    @Mock
    private SeleccionFabrica seleccionFabrica;  // Mock de la fábrica para seleccionar tipos de elementos

    @Mock
    private ElementosDtiFabrica fabricaMock;  // Mock de la fábrica para crear elementos

    @InjectMocks
    private ElementosImpl elementosService;  // Clase bajo prueba, que tendrá los mocks inyectados

    private ElementosDti elementoActivo;

    // Configuración inicial antes de cada prueba
    @BeforeEach
    void setUp() {
        // Inicializa los mocks antes de cada prueba
        MockitoAnnotations.openMocks(this);

        // Crear un elemento activo para usar en las pruebas
        elementoActivo = new ElementosDti("123", "Elemento Test", "Descripción del elemento",
                TipoDeElementos.Computador, EstadosElementos.DISPONIBLE, "Ubicación Test", LocalDate.now()) {
            @Override
            public TipoDeElementos getTipo() {
                return TipoDeElementos.Computador;  // Simula el comportamiento del método abstracto
            }
        };
    }

    @Test
    void crearElemento_ShouldReturnElement_WhenElementIsCreated() {
        // Configura el comportamiento del mock para seleccionar la fábrica adecuada
        when(seleccionFabrica.obtenerFabrica(TipoDeElementos.Computador)).thenReturn(fabricaMock);

        // Configura el comportamiento del mock para la creación del elemento
        when(fabricaMock.crearElementoDti(anyString(), anyString(), anyString(), eq(TipoDeElementos.Computador),
                eq(EstadosElementos.DISPONIBLE), anyString(), eq(LocalDate.now())))
                .thenReturn(elementoActivo);

        // Llama al método que se está probando
        ElementosDti result = elementosService.crearElemento("123", "Computador Test", "Descripción del computador",
                TipoDeElementos.Computador, EstadosElementos.DISPONIBLE, "Ubicación Test");

        // Verifica que el resultado no sea nulo y que los valores sean correctos
        assertNotNull(result);
        //assertEquals("Computador Test", result.getNombre());
        assertEquals(EstadosElementos.DISPONIBLE, result.getEstado());
        assertEquals(TipoDeElementos.Computador, result.getTipo());
        assertEquals("Ubicación Test", result.getUbicacion());

        // Verifica que el método obtenerFabrica fue llamado con el tipo correcto
        verify(seleccionFabrica, times(1)).obtenerFabrica(TipoDeElementos.Computador);

        // Verifica que el método crearElementoDti fue llamado con los parámetros correctos
        verify(fabricaMock, times(1)).crearElementoDti("123", "Computador Test", "Descripción del computador",
                TipoDeElementos.Computador, EstadosElementos.DISPONIBLE,
                "Ubicación Test", LocalDate.now());

        // Verifica que el repositorio save fue llamado una vez con el elemento creado
        verify(repository, times(1)).save(elementoActivo);
    }

    /*Simula la consulta de un elemento por su identificador y verifica que se retorne el elemento correcto.
    Asegura que el repositorio findById haya sido llamado correctamente*/

    @Test
    void VerDetalles_ShouldReturnElemento_WhenElementoExists() {
        // Configura el comportamiento del repositorio mockeado para devolver un elemento encontrado
        when(repository.findById("E001")).thenReturn(Optional.of(new ElementosDti("E001", "Control",
                "Control de PC", TipoDeElementos.Control, EstadosElementos.EN_PRESTAMO, "A1", LocalDate.now()) {
            @Override
            public TipoDeElementos getTipo() {
                return TipoDeElementos.Control;
            }
        }));

        // Llama al método 'VerDetalles' y verifica que devuelva el elemento correcto
        ElementosDti result = elementosService.VerDetalles("E001");

        // Verifica que el resultado no sea nulo y que los valores sean los esperados
        assertNotNull(result);
        assertEquals("E001", result.getIdentificacion());
        assertEquals("Control", result.getNombre());
        assertEquals(TipoDeElementos.Control, result.getTipo());

        // Verifica que el repositorio 'findById' fue llamado una vez
        verify(repository, times(1)).findById("E001");
    }


    /*Esta prueba simula un escenario donde el elemento no se encuentra en la base de datos y
    verifica que se lanza la excepción NoSuchElementException con el mensaje adecuado.*/
    @Test
    void VerDetalles_ShouldThrowException_WhenElementoNotFound() {
        // Configura el comportamiento del repositorio mockeado para simular que el elemento no se encuentra
        when(repository.findById("E001")).thenReturn(Optional.empty());

        // Verifica que se lanza una excepción NoSuchElementException
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            elementosService.VerDetalles("E001");
        });

        // Verifica que el mensaje de la excepción sea el esperado
        assertEquals("Elemento con ID E001 no encontrado", exception.getMessage());
    }


    /*Simula la edición de un elemento, asegurando que los cambios se apliquen correctamente
    a los campos nombre, descripcion, estado y ubicacion.*/
    @Test
    void EditarElemento_ShouldUpdateElementoSuccessfully() {
        // Configura el comportamiento del repositorio mockeado para devolver un elemento
        when(repository.findById("E001")).thenReturn(Optional.of(new ElementosDti("E001", "Control",
                "Control de PC", TipoDeElementos.Control, EstadosElementos.EN_PRESTAMO, "A1", LocalDate.now()) {
            @Override
            public TipoDeElementos getTipo() {
                return TipoDeElementos.Control;
            }
        }));

        // Configura el comportamiento del repositorio para guardar el elemento actualizado
        when(repository.save(any(ElementosDti.class))).thenReturn(new ElementosDti("E001", "Control Modificado",
                "Control modificado", TipoDeElementos.Control, EstadosElementos.DISPONIBLE, "B1", LocalDate.now()) {
            @Override
            public TipoDeElementos getTipo() {
                return null;
            }
        });

        // Llama al método 'EditarElemento' y verifica que el elemento haya sido actualizado
        ElementosDti result = elementosService.EditarElemento("E001", "Control Modificado",
                "Control modificado", EstadosElementos.DISPONIBLE, "B1");

        // Verifica que los valores hayan sido actualizados correctamente
        assertNotNull(result);
        assertEquals("Control Modificado", result.getNombre());
        assertEquals("Control modificado", result.getDescripcion());
        assertEquals(EstadosElementos.DISPONIBLE, result.getEstado());
        assertEquals("B1", result.getUbicacion());

        // Verifica que el repositorio 'findById' y 'save' hayan sido llamados correctamente
        verify(repository, times(1)).findById("E001");
        verify(repository, times(1)).save(any(ElementosDti.class));
    }

    /*Simula la eliminación de un elemento y asegura que el repositorio delete sea llamado correctamente.*/
    @Test
    void EliminarElemento_ShouldDeleteElementoSuccessfully() {
        // Configura el comportamiento del repositorio mockeado para devolver un elemento
        when(repository.findById("E001")).thenReturn(Optional.of(new ElementosDti("E001", "Control",
                "Control de PC", TipoDeElementos.Control, EstadosElementos.EN_PRESTAMO, "A1", LocalDate.now()) {
            @Override
            public TipoDeElementos getTipo() {
                return TipoDeElementos.Control;
            }
        }));

        // Llama al método 'EliminarElemento' y verifica que el elemento haya sido eliminado correctamente
        ElementosDti result = elementosService.EliminarElemento("E001");

        // Verifica que el elemento retornado sea el esperado
        assertNotNull(result);
        assertEquals("E001", result.getIdentificacion());

        // Verifica que el repositorio 'findById' y 'delete' hayan sido llamados
        verify(repository, times(1)).findById("E001");
        verify(repository, times(1)).delete(any(ElementosDti.class));
    }

    /*Esta prueba simula el cambio de estado de un elemento, verificando que el estado
    y la ubicación sean actualizados correctamente en el repositorio.*/
    @Test
    void CambiarEstado_ShouldChangeEstadoSuccessfully() {
        // Configura el comportamiento del repositorio mockeado para devolver un elemento
        when(repository.findById("E001")).thenReturn(Optional.of(new ElementosDti("E001", "Control",
                "Control de PC", TipoDeElementos.Control, EstadosElementos.EN_PRESTAMO, "A1", LocalDate.now()) {
            @Override
            public TipoDeElementos getTipo() {
                return null;
            }
        }));

        // Configura el comportamiento del repositorio para guardar el elemento con el nuevo estado
        when(repository.save(any(ElementosDti.class))).thenReturn(new ElementosDti("E001", "Control",
                "Control de PC", TipoDeElementos.Control, EstadosElementos.DISPONIBLE, "B1", LocalDate.now()) {
            @Override
            public TipoDeElementos getTipo() {
                return null;
            }
        });

        // Llama al método 'CambiarEstado' y verifica que el estado y ubicación hayan sido cambiados correctamente
        ElementosDti result = elementosService.CambiarEstado("E001", EstadosElementos.DISPONIBLE, "B1");

        // Verifica que los valores hayan sido actualizados
        assertNotNull(result);
        assertEquals(EstadosElementos.DISPONIBLE, result.getEstado());
        assertEquals("B1", result.getUbicacion());

        // Verifica que el repositorio 'findById' y 'save' hayan sido llamados correctamente
        verify(repository, times(1)).findById("E001");
        verify(repository, times(1)).save(any(ElementosDti.class));
    }

}
