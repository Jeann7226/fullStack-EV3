package fullStack_EV3.repository;

import fullStack_EV3.model.Comuna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComunaRepository extends JpaRepository<Comuna, Long> {
    List<Comuna> findByRegionIdRegion(Long idRegion);
    Optional<Comuna> findByNombreComuna(String nombreComuna);
}
