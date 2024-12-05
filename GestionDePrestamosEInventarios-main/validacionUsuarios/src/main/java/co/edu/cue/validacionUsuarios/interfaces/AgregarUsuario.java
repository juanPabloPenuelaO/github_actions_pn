package co.edu.cue.validacionUsuarios.interfaces;

import co.edu.cue.validacionUsuarios.enums.Estados;
import co.edu.cue.validacionUsuarios.model.Usuario;

public interface AgregarUsuario {
    Usuario agregarUsuario(String nombre,String correo, Estados condicion, String descripcion);
}
