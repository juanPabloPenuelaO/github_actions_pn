package co.edu.cue.validacionUsuarios.interfaces;

import co.edu.cue.validacionUsuarios.enums.Estados;
import co.edu.cue.validacionUsuarios.model.Usuario;

public interface EditarUsuario {
    Usuario EditarUsuario(String correo, Estados condicion, String descripcion, String nombre);
}
