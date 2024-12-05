package co.edu.cue.validacionUsuarios.controller;

import co.edu.cue.validacionUsuarios.model.Usuario;
import co.edu.cue.validacionUsuarios.request.RespuestaApi;
import co.edu.cue.validacionUsuarios.request.UsuarioRequest;
import co.edu.cue.validacionUsuarios.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    //Agregar un nuevo usuario a la lista de personas con limitacion de acceso al sistema
    @PostMapping("/agregar")
    public ResponseEntity<RespuestaApi<Usuario>> agregarUsuario(@Valid @RequestBody UsuarioRequest request){
        try{
            //llama la funcion agregar usuario del service y guarda el usuario que llega como retorno
           Usuario usuario = service.agregarUsuario(
                    request.getNombre(),
                    request.getCorreo(),
                    request.getCondicion(),
                    request.getDescripcion()
                    );
           RespuestaApi<Usuario> response = new RespuestaApi<>(
                    "OK",
                    "Usuario creado exitosamente",
                    usuario //el usuario que se genero de respuesta se guarda en el respuesta
           );
           return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            RespuestaApi<Usuario> errorResponse = new RespuestaApi<>(
                    "ERROR",
                    "Error al crear el usuario: " + e.getMessage(),
                    null //como no se recupero ningun usuario se manda vacio el dato
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    //Se actualiza un usuario, buscandolo con el correo
    @PutMapping("/actualizar/{correo}")
    public ResponseEntity<RespuestaApi<Usuario>> ActualizarElemento(
            @PathVariable("correo") String correo,
            @RequestBody UsuarioRequest request) {
        try {
            Usuario usuario = service.EditarUsuario(
                    correo, //el correo llega por medio del pathVarible por eso no se extre del request
                    request.getCondicion(),
                    request.getDescripcion(),
                    request.getNombre()
            );
            RespuestaApi<Usuario> response = new RespuestaApi<>(
                    "OK",
                    "Elemento actualizado exitosamente",
                    usuario // El objeto creado se pasa directamente como `data`
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }  catch (NoSuchElementException e) {
            // Captura la excepción cuando no se encuentra el elemento
            RespuestaApi<Usuario> errorResponse = new RespuestaApi<>(
                    "ERROR",
                    "Usuario no encontrado",
                    null //como no se recupero ningun usuario se manda vacio el dato
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            // Captura cualquier otro tipo de error general
            RespuestaApi<Usuario> errorResponse = new RespuestaApi<>(
                    "ERROR",
                    "Error al actualizar el usuario: " + e.getMessage(),
                    null //como no se recupero ningun usuario se manda vacio el dato
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/consultar/{correo}")
    public ResponseEntity<RespuestaApi<Usuario>> consultarUsuario(
            @PathVariable("correo") String correo) {
        try {
            Usuario usuario = service.consultar(correo);
            RespuestaApi<Usuario> response = new RespuestaApi<>(
                    "OK",
                    "El usuario "+correo+" esta en estado "+usuario.getCondicion(),
                    usuario // El objeto creado se pasa directamente como `data`
            );
            return ResponseEntity.status(HttpStatus.FOUND).body(response);
        } catch (NoSuchElementException e) {
            // Captura la excepción cuando no se encuentra el elemento
            RespuestaApi<Usuario> errorResponse = new RespuestaApi<>(
                    "ERROR",
                    "Usuario no encontrado",
                    null //como no se recupero ningun usuario se manda vacio el dato
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            // Captura cualquier otro tipo de error general
            RespuestaApi<Usuario> errorResponse = new RespuestaApi<>(
                    "ERROR",
                    "Error al buscar al usuario: " + e.getMessage(),
                    null //como no se recupero ningun usuario se manda vacio el dato
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping("/eliminar/{correo}")
    public ResponseEntity<RespuestaApi<Usuario>> deleteElement(@PathVariable("correo") String correo) {
        try {
            Usuario usuario = service.EliminarUsuario(correo);
            RespuestaApi<Usuario> response = new RespuestaApi<>(
                    "OK",
                    "Usuario con correo "+correo+" fue eliminado exitosamente",
                    usuario // El objeto creado se pasa directamente como `data`
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (NoSuchElementException e) {
            // Captura la excepción cuando no se encuentra el elemento
            RespuestaApi<Usuario> errorResponse = new RespuestaApi<>(
                    "ERROR",
                    "Usuario no encontrado",
                    null //como no se recupero ningun usuario se manda vacio el dato
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            // Captura cualquier otro tipo de error general
            RespuestaApi<Usuario> errorResponse = new RespuestaApi<>(
                    "ERROR",
                    "Error al eliminar el usuario: " + e.getMessage(),
                    null //como no se recupero ningun usuario se manda vacio el dato
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
