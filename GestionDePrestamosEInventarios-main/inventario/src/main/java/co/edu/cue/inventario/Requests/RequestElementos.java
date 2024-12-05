package co.edu.cue.inventario.Requests;

import co.edu.cue.inventario.Enums.EstadosElementos;
import co.edu.cue.inventario.Enums.TipoDeElementos;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class RequestElementos {

    @NotNull(message = "El elemento debe contener un nombre")
    private String nombre;

    @NotNull(message = "El elemento debe contener una identificacion")
    private String identificacion;
    private String descripcion;

    private String ubicacion;

    @NotNull(message = "El tipo es obligatorio")
    private TipoDeElementos tipo;

    @NotNull(message = "El estado es obligatorio")
    private EstadosElementos estado;

   /* @NotNull(message = "La fecha de creación es obligatoria")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaCreacion;*/

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

    public TipoDeElementos getTipo() {return tipo;}

    public void setTipo(TipoDeElementos tipo) {this.tipo = tipo;}

    public EstadosElementos getEstado() {
        return estado;
    }

    public void setEstado(EstadosElementos estado) {
        this.estado = estado;
    }

/*    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }*/

    public String getUbicacion() {return ubicacion;}

    public void setUbicacion(String ubicacion) {this.ubicacion = ubicacion;}

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }
}
