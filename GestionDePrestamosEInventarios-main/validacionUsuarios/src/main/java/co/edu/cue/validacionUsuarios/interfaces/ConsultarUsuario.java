package co.edu.cue.validacionUsuarios.interfaces;

import co.edu.cue.validacionUsuarios.model.Usuario;

public interface ConsultarUsuario {
    Usuario consultar(String correo);
}
