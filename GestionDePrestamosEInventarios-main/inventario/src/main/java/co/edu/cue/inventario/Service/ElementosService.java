package co.edu.cue.inventario.Service;

import co.edu.cue.inventario.ElementosDti.ElementosDti;
import co.edu.cue.inventario.Enums.EstadosElementos;
import co.edu.cue.inventario.Enums.TipoDeElementos;

import java.time.LocalDate;

public interface ElementosService {
    ElementosDti crearElemento(String identificacion, String nombre, String descripcion, TipoDeElementos tipo, EstadosElementos estado, String ubicacion);

    ElementosDti VerDetalles(String identificacion);

    ElementosDti EditarElemento(String identificacion, String nombre, String descripcion, EstadosElementos estado, String ubicacion);

    ElementosDti EliminarElemento(String identificacion);

    ElementosDti CambiarEstado(String identificacion, EstadosElementos estado, String ubicacion);
}
