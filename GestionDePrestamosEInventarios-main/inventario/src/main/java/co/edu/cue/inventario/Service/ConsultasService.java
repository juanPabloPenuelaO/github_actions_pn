package co.edu.cue.inventario.Service;

import co.edu.cue.inventario.ElementosDti.ElementosDti;
import co.edu.cue.inventario.Enums.EstadosElementos;
import co.edu.cue.inventario.Enums.TipoDeElementos;

import java.util.List;

public interface ConsultasService {
    List<ElementosDti> filtrarPorTipo(TipoDeElementos tipo);
    List<ElementosDti> filtrarPorEstado(EstadosElementos estado);
    Boolean existeElemento(String id);
}
