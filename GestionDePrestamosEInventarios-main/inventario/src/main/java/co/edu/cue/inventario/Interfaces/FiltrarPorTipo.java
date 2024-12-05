package co.edu.cue.inventario.Interfaces;

import co.edu.cue.inventario.ElementosDti.ElementosDti;
import co.edu.cue.inventario.Enums.TipoDeElementos;

import java.util.List;

public interface FiltrarPorTipo {
    List<ElementosDti> filtrarPorTipo(TipoDeElementos tipo);
}
