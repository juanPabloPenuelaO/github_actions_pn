package co.edu.cue.inventario.repository;

import co.edu.cue.inventario.ElementosDti.ElementosDti;
import co.edu.cue.inventario.Enums.EstadosElementos;
import co.edu.cue.inventario.Enums.TipoDeElementos;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventarioRepository extends MongoRepository<ElementosDti, String> {

    List<ElementosDti> findByEstado(EstadosElementos estado);
    List<ElementosDti> findByTipo(TipoDeElementos tipo);
}
