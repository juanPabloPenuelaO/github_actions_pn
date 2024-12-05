package co.edu.cue.validacionUsuarios.service;

import co.edu.cue.validacionUsuarios.enums.Estados;
import co.edu.cue.validacionUsuarios.model.Usuario;

public interface UsuarioService {
    Usuario agregarUsuario(String nombre,String correo, Estados condicion, String descripcion);
    Usuario consultar (String correo);
    Usuario EditarUsuario( String correo, Estados condicion, String descripcion, String nombre);
    Usuario EliminarUsuario(String id);
}
