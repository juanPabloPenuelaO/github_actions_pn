package co.edu.cue.inventario.Service.Implementaciones;

import co.edu.cue.inventario.Configuracion.MensajeUtil;
import co.edu.cue.inventario.ElementosDti.ElementosDti;
import co.edu.cue.inventario.Enums.EstadosElementos;
import co.edu.cue.inventario.Enums.TipoDeElementos;
import co.edu.cue.inventario.Interfaces.FiltrarEstado;
import co.edu.cue.inventario.Interfaces.FiltrarPorTipo;
import co.edu.cue.inventario.Service.ConsultasService;
import co.edu.cue.inventario.repository.InventarioRepository;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class ConsultasImpl implements FiltrarEstado, FiltrarPorTipo, ConsultasService {

    private final InventarioRepository repository;
    private final MensajeUtil mensajes;

    public ConsultasImpl(InventarioRepository repository, MensajeUtil mensajes) {
        this.repository = repository;
        this.mensajes = mensajes;
    }

    private String getMensaje(String codigo) {
        return mensajes.getMensaje(
                codigo,
                LocaleContextHolder.getLocale()
        );
    }

    @GetMapping
    public List<ElementosDti> filtrarPorTipo(TipoDeElementos tipo) {
        try {
            return repository.findByTipo(tipo);
        } catch (Exception e) {
            throw new RuntimeException("Error al realizar la búsqueda");
        }
    }

    @GetMapping
    public List<ElementosDti> filtrarPorEstado(EstadosElementos estado) {
        try {
            return repository.findByEstado(estado);
        } catch (Exception e) {
            throw new RuntimeException("Error al realizar la búsqueda");
        }
    }

    @GetMapping
    public Boolean existeElemento(String id){
        try {
            return repository.existsById(id);
        } catch (Exception e){
            throw new RuntimeException("Error al realizar la búsqueda");
        }
    }
}
