package co.edu.cue.inventario.Service.Implementaciones;

import co.edu.cue.inventario.ElementosDti.ElementosDti;
import co.edu.cue.inventario.Enums.EstadosElementos;
import co.edu.cue.inventario.Enums.TipoDeElementos;
import co.edu.cue.inventario.Fabricas.ElementosDtiFabrica;
import co.edu.cue.inventario.Fabricas.SeleccionFabrica;
import co.edu.cue.inventario.Interfaces.*;
import co.edu.cue.inventario.Service.ElementosService;
import co.edu.cue.inventario.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.time.LocalDate;
import java.util.NoSuchElementException;

@Service
public class ElementosImpl implements CrearElemento, EditarElementos, EliminarElementos, VerDetalles, CambiarEstado, ElementosService {
    private final InventarioRepository repository;

    private ElementosDti elemento;
    @Autowired
    private final SeleccionFabrica seleccionFabrica;

    public ElementosImpl(InventarioRepository repository, SeleccionFabrica seleccionFabrica) {
        this.repository = repository;
        this.seleccionFabrica = seleccionFabrica;
    }

    @PostMapping
    @Override
    public ElementosDti crearElemento(String identificacion, String nombre, String descripcion, TipoDeElementos tipo,
                                      EstadosElementos estado, String ubicacion){

        //Selecciona la fabrica de acuerdo al tipo de elemento que se quiere crear
        ElementosDtiFabrica fabrica = seleccionFabrica.obtenerFabrica(tipo);

        //llama a la fabrica seleccionada y crea el elemento
        this.elemento = fabrica.crearElementoDti(identificacion, nombre, descripcion, tipo, estado,ubicacion, LocalDate.now());

        //guarda el nuevo elemento en la BD y lo retorna para mostrarlo
        repository.save(elemento);
        return elemento;
    }

    @GetMapping
    @Override
    public ElementosDti VerDetalles(String identificacion){

        //busca el elementos que se quiere mostrar
        return repository.findById(identificacion)
                .orElseThrow(() -> new NoSuchElementException("Elemento con ID " + identificacion + " no encontrado"));
    }

    @PutMapping
    @Override
    public ElementosDti EditarElemento(String identificacion, String nombre, String descripcion,
                                       EstadosElementos estado, String ubicacion){

        //busca el elemento que se quiere modificar
        elemento = repository.findById(identificacion)
                .orElseThrow(() -> new NoSuchElementException("Elemento no encontrado"));

        // Actualiza los campos necesarios
        elemento.setNombre(nombre);
        elemento.setDescripcion(descripcion);
        elemento.setEstado(estado);
        elemento.setUbicacion(ubicacion);
        elemento.setFechaModificacion(LocalDate.now());

        // Guarda los cambios
        repository.save(elemento);
        return elemento;

    }

    @DeleteMapping
    @Override
    public ElementosDti EliminarElemento(String identificacion){
        elemento = repository.findById(identificacion)
                .orElseThrow(() -> new NoSuchElementException("Elemento con ID " + identificacion + " no encontrado"));

        // Eliminar el elemento
        repository.delete(elemento);
        return elemento;
    }
    @PutMapping
    @Override
    public ElementosDti CambiarEstado(String identificacion, EstadosElementos estado, String ubicacion){
        elemento = repository.findById(identificacion)
                .orElseThrow(()-> new NoSuchElementException("No se ha podido cambiar el estado del elemento "+ identificacion));

        //Se edita solamente el estado y su ubicación, cuando cambia el estado del préstamo
        elemento.setEstado(estado);
        elemento.setUbicacion(ubicacion);
        elemento.setFechaModificacion(LocalDate.now());

        //Guarda los cambios en el estado y la ubicacion, cambia la fecha de modificacion
        repository.save(elemento);
        return elemento;
    }

}
