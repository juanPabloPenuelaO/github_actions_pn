package co.edu.cue.inventario.Interfaces;

import co.edu.cue.inventario.ElementosDti.ElementosDti;
import co.edu.cue.inventario.Enums.EstadosElementos;

import java.util.List;

public interface FiltrarEstado {
    List<ElementosDti> filtrarPorEstado(EstadosElementos estado);
}
