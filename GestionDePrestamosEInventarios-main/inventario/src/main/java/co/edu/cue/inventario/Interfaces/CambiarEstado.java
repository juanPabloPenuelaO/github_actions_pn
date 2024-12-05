package co.edu.cue.inventario.Interfaces;


import co.edu.cue.inventario.ElementosDti.ElementosDti;
import co.edu.cue.inventario.Enums.EstadosElementos;

public interface CambiarEstado {
    ElementosDti CambiarEstado(String identificacion, EstadosElementos estado, String ubicacion);

}
