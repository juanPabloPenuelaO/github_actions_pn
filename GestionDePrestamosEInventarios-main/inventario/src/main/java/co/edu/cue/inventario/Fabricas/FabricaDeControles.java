package co.edu.cue.inventario.Fabricas;

import co.edu.cue.inventario.ElementosDti.Controles;
import co.edu.cue.inventario.ElementosDti.ElementosDti;
import co.edu.cue.inventario.Enums.EstadosElementos;
import co.edu.cue.inventario.Enums.TipoDeElementos;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
//@Qualifier("fabricaDeControles")
public class FabricaDeControles implements ElementosDtiFabrica{
    @Override
    public ElementosDti crearElementoDti(String identificacion, String nombre, String descripcion, TipoDeElementos tipo,
                                         EstadosElementos estado, String ubicacion,LocalDate fechaCreacion) {
        return new Controles(identificacion, nombre, descripcion, tipo, estado, ubicacion, fechaCreacion);
    }
}
