package fullStack_EV3.repository;

import fullStack_EV3.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByMarcaIdMarca(Long idMarca);
}
