package co.edu.cue.validacionUsuarios.service.Impl;

import co.edu.cue.validacionUsuarios.enums.Estados;
import co.edu.cue.validacionUsuarios.interfaces.AgregarUsuario;
import co.edu.cue.validacionUsuarios.interfaces.ConsultarUsuario;
import co.edu.cue.validacionUsuarios.interfaces.EditarUsuario;
import co.edu.cue.validacionUsuarios.interfaces.EliminarUsuario;
import co.edu.cue.validacionUsuarios.model.Usuario;
import co.edu.cue.validacionUsuarios.repository.UsuarioRepository;
import co.edu.cue.validacionUsuarios.service.UsuarioService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.NoSuchElementException;

@Service
public class UsuarioImpl implements UsuarioService, AgregarUsuario, EliminarUsuario, ConsultarUsuario, EditarUsuario {

    private final UsuarioRepository repository;

    private Usuario persona;

    public UsuarioImpl(UsuarioRepository repository) {this.repository = repository;}

    @PostMapping
    @Override
    public Usuario agregarUsuario(String nombre,String correo, Estados condicion, String descripcion){

        persona = new Usuario(nombre, correo,condicion, descripcion);
        repository.save(persona);
        return persona;
    }

    @GetMapping
    @Override
    public Usuario consultar (String correo){
        try {
            return repository.findByCorreo(correo);
        }catch (Exception e){
            throw new RuntimeException("Persona con correo " + correo + " no encontrado");
        }
    }

    @PutMapping
    @Override
    public Usuario EditarUsuario(String correo, Estados condicion, String descripcion, String nombre){
        try {
            persona= repository.findByCorreo(correo);
            persona.setCondicion(condicion);
            persona.setCorreo(correo);
            persona.setDescripcion(descripcion);
            persona.setNombre(nombre);

            repository.save(persona);
            return persona;

        }catch (Exception e){
            throw new RuntimeException("Persona con correo " + correo + " no encontrado");
        }

    }

    @DeleteMapping
    @Override
    public Usuario EliminarUsuario(String correo){
        try {
            persona = repository.findByCorreo(correo);

            repository.delete(persona);
            return persona;

        }catch (Exception e){
            throw new RuntimeException("Se genero un error al buscar el correo");
        }
    }
}
