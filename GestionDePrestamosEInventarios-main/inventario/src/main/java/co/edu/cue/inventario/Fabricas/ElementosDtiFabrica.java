package co.edu.cue.inventario.Fabricas;

import co.edu.cue.inventario.ElementosDti.ElementosDti;
import co.edu.cue.inventario.Enums.EstadosElementos;
import co.edu.cue.inventario.Enums.TipoDeElementos;
import org.springframework.context.annotation.Bean;

import java.beans.BeanProperty;
import java.time.LocalDate;


public interface ElementosDtiFabrica {

    ElementosDti crearElementoDti(String identificacion, String nombre, String descripcion, TipoDeElementos tipo, EstadosElementos estado, String ubicacion,LocalDate fechaCreacion);

}
