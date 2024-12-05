package co.edu.cue.validacionUsuarios.model;

import co.edu.cue.validacionUsuarios.enums.Estados;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ListaNegraUsuarios")
@ToString
public class Usuario {

    private String nombre;
    @Id
    private String id;
    private String correo;
    private Estados condicion;
    private String descripcion;

    public Usuario(String nombre, String correo, Estados condicion, String descripcion) {
        this.nombre = nombre;
        this.condicion = condicion;
        this.descripcion = descripcion;
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Estados getCondicion() {
        return condicion;
    }

    public void setCondicion(Estados condicion) {
        this.condicion = condicion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
