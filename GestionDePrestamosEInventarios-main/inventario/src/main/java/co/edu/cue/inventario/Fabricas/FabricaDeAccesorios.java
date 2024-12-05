package co.edu.cue.inventario.Fabricas;

import co.edu.cue.inventario.ElementosDti.AccesoriosMaterialDeSoporte;
import co.edu.cue.inventario.ElementosDti.ElementosDti;
import co.edu.cue.inventario.Enums.EstadosElementos;
import co.edu.cue.inventario.Enums.TipoDeElementos;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
@Component
@Primary
//@Qualifier("fabricaDeAccesorios")
public class FabricaDeAccesorios implements ElementosDtiFabrica{

    @Override
    public ElementosDti crearElementoDti(String identificacion, String nombre, String descripcion, TipoDeElementos tipo,
                                         EstadosElementos estado, String ubicacion, LocalDate fechaCreacion) {
        return new AccesoriosMaterialDeSoporte(identificacion, nombre, descripcion, tipo , estado,ubicacion, fechaCreacion);
    }
}
