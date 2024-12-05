package co.edu.cue.validacionUsuarios.repository;

import co.edu.cue.validacionUsuarios.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario,String> {
    Usuario findByCorreo(String correo);
}
