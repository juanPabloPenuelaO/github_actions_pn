package co.edu.cue.validacionUsuarios;


import co.edu.cue.validacionUsuarios.enums.Estados;
import co.edu.cue.validacionUsuarios.model.Usuario;
import co.edu.cue.validacionUsuarios.repository.UsuarioRepository;
import co.edu.cue.validacionUsuarios.service.Impl.UsuarioImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/*Esta clase contiene pruebas unitarias para la clase UsuarioImpl, que maneja las operaciones
CRUD (crear, leer, actualizar y eliminar) de usuarios. Cada prueba está diseñada para verificar
el comportamiento de los métodos en diferentes situaciones.*/

class UsuarioImplTest {

    /*Crea un objeto simulado (mock) del UsuarioRepository para no interactuar
    con una base de datos real durante las pruebas.*/
    @Mock
    private UsuarioRepository repository; // Simulación del repositorio

    /* Inyecta el mock del repositorio en la clase UsuarioImpl para poder probar su lógica.*/
    @InjectMocks
    private UsuarioImpl usuarioService;  // Inyección del mock en la clase bajo prueba

    private Usuario usuarioActivo;


    /*Configura el entorno para cada prueba, inicializando los mocks y los objetos necesarios antes de cada ejecución.*/
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa los mocks antes de cada prueba
        usuarioActivo = new Usuario("Juan", "juan@dominio.com",  Estados.ACTIVO, "Descripción activa");
    }

    /*Simula la creación de un usuario y verifica que el repositorio save haya sido invocado correctamente.
    Verifica que los valores del usuario (nombre, correo, estado) sean correctos.*/

    @Test
    void agregarUsuario_ShouldSaveUsuario() {
        // Configura el comportamiento del repositorio mockeado
        when(repository.save(any(Usuario.class))).thenReturn(usuarioActivo);

        // Llama al método que se está probando
        Usuario result = usuarioService.agregarUsuario("Juan", "juan@dominio.com", Estados.ACTIVO, "Descripción activa");

        // Verifica el comportamiento
        assertNotNull(result);
        assertEquals("Juan", result.getNombre());
        assertEquals(Estados.ACTIVO, result.getCondicion());
        assertEquals("juan@dominio.com", result.getCorreo());

        // Verifica que el repositorio save fue llamado una vez
        verify(repository, times(1)).save(any(Usuario.class));
    }

    /*Simula la consulta de un usuario por su correo y verifica que el usuario encontrado tenga los valores correctos.
    Asegura que el repositorio findByCorreo se haya invocado correctamente.*/

    @Test
    public void consultar_ShouldReturnUsuario_WhenUsuarioExists() {
        // Configura el comportamiento del repositorio mockeado
        when(repository.findByCorreo("juan@dominio.com")).thenReturn(usuarioActivo);

        // Llama al método que se está probando
        Usuario result = usuarioService.consultar("juan@dominio.com");

        // Verifica el comportamiento
        assertNotNull(result);
        assertEquals("Juan", result.getNombre());
        assertEquals("juan@dominio.com", result.getCorreo());
        assertEquals(Estados.ACTIVO, result.getCondicion());

        // Verifica que el repositorio findByCorreo fue llamado
        verify(repository, times(1)).findByCorreo("juan@dominio.com");
    }

    /*Simula un escenario en el que no se encuentra un usuario y asegura que se lanza una excepción con el mensaje adecuado.*/

    @Test
    public void consultar_ShouldThrowException_WhenUsuarioNotFound() {
        // Configura el comportamiento del repositorio mockeado para simular que no se encuentra el usuario
        when(repository.findByCorreo("juan@dominio.com")).thenThrow(new RuntimeException("Persona con correo juan@dominio.com no encontrado"));

        // Verifica que se lanza la excepción esperada
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            usuarioService.consultar("juan@dominio.com");
        });

        // Verifica el mensaje de la excepción
        assertEquals("Persona con correo juan@dominio.com no encontrado", exception.getMessage());
    }

    /*Simula la actualización de un usuario y asegura que la información del usuario haya sido modificada correctamente.
    Verifica que los métodos findByCorreo y save se hayan llamado una vez*/

    @Test
    public void editarUsuario_ShouldUpdateUsuario() {
        // Configura el comportamiento del repositorio mockeado
        when(repository.findByCorreo("juan@dominio.com")).thenReturn(usuarioActivo);
        when(repository.save(any(Usuario.class))).thenReturn(usuarioActivo);

        // Llama al método que se está probando
        Usuario result = usuarioService.EditarUsuario("juan@dominio.com", Estados.INACTIVO, "Descripción modificada", "Juan Modificado");

        // Verifica que los datos del usuario hayan sido actualizados
        assertNotNull(result);
        assertEquals("Juan Modificado", result.getNombre());
        assertEquals(Estados.INACTIVO, result.getCondicion());
        assertEquals("Descripción modificada", result.getDescripcion());

        // Verifica que el repositorio findByCorreo y save fueron llamados
        verify(repository, times(1)).findByCorreo("juan@dominio.com");
        verify(repository, times(1)).save(any(Usuario.class));
    }

    /*Simula un escenario en el que el usuario no se encuentra y verifica que se lanza una excepción con el mensaje adecuado.*/
    @Test()
    public void editarUsuario_ShouldThrowException_WhenUsuarioNotFound() {
        // Configura el comportamiento del repositorio mockeado para simular que no se encuentra el usuario
        when(repository.findByCorreo("juan@dominio.com")).thenThrow(new RuntimeException("Persona con correo juan@dominio.com no encontrado"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            usuarioService.EditarUsuario("juan@dominio.com", Estados.INACTIVO, "Descripción modificada", "Juan Modificado");
        });

        // Llama al método que se está probando y espera que lance una excepción
        assertEquals("Persona con correo juan@dominio.com no encontrado", exception.getMessage());    }

    /*Simula la eliminación de un usuario y asegura que se elimina correctamente.
    Verifica que los métodos findByCorreo y delete se hayan llamado.*/
    @Test
    public void eliminarUsuario_ShouldDeleteUsuario() {
        // Configura el comportamiento del repositorio mockeado
        when(repository.findByCorreo("juan@dominio.com")).thenReturn(usuarioActivo);

        // Llama al método que se está probando
        Usuario result = usuarioService.EliminarUsuario("juan@dominio.com");

        // Verifica que el usuario fue eliminado correctamente
        assertNotNull(result);
        assertEquals("juan@dominio.com", result.getCorreo());

        // Verifica que el repositorio findByCorreo y delete fueron llamados
        verify(repository, times(1)).findByCorreo("juan@dominio.com");
        verify(repository, times(1)).delete(usuarioActivo);
    }

    /*Simula el caso en el que no se encuentra el usuario y verifica que se lance la excepción correspondiente.*/
    @Test
    public void eliminarUsuario_ShouldThrowException_WhenUsuarioNotFound() {
        // Configura el comportamiento del repositorio mockeado para simular que no se encuentra el usuario
        when(repository.findByCorreo("juan@dominio.com")).thenThrow(new RuntimeException("Se genero un error al buscar el correo"));

        // Verifica que se lanza la excepción esperada cuando se llama al método EliminarUsuario
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            usuarioService.EliminarUsuario("juan@dominio.com");
        });

        // Verifica el mensaje de la excepción
        assertEquals("Se genero un error al buscar el correo", exception.getMessage());
    }

}

