package fullStack_EV3.repository;

import fullStack_EV3.model.Boleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoletaRepository extends JpaRepository<Boleta, Long> {
    Optional<Boleta> findByNumeroBoleta(String numeroBoleta);
    Optional<Boleta> findByPagoIdPago(Long idPago);
}
