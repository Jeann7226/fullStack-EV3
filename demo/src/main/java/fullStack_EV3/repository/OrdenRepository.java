package fullStack_EV3.repository;

import fullStack_EV3.model.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, String> {
    List<Orden> findByUsuarioRun(String run);
    List<Orden> findByEstadoNombreEstado(String nombreEstado);
}
