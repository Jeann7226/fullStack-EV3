package fullStack_EV3.repository;

import fullStack_EV3.model.CategoriaProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaProductoRepository extends JpaRepository<CategoriaProducto, Long> {
    List<CategoriaProducto> findByProductoIdProducto(Long idProducto);
    List<CategoriaProducto> findByCategoriaIdCategoria(Long idCategoria);
    void deleteByProductoIdProducto(Long idProducto);
}
