package co.edu.cue.validacionUsuarios.request;

import co.edu.cue.validacionUsuarios.enums.Estados;
import jakarta.validation.constraints.NotNull;


public class UsuarioRequest {

    @NotNull(message = "El usuario debe tener un nombre")
    private String nombre;
    @NotNull(message = "El usuario debe tener una condicion")
    private Estados condicion;
    @NotNull(message = "El usuario debe tener una descripcion")
    private String descripcion;

    @NotNull(message = "El usuario debe tener un correo")
    private String correo;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Estados getCondicion() {
        return condicion;
    }

    public void setCondicion(Estados condicion) {
        this.condicion = condicion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
