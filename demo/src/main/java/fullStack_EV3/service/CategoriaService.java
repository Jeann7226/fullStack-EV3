package fullStack_EV3.service;

import fullStack_EV3.dto.CategoriaDto;
import fullStack_EV3.model.Categoria;
import fullStack_EV3.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional(readOnly = true)
    public List<CategoriaDto> getAll() {
        return categoriaRepository.findAll().stream()
                .map(c -> new CategoriaDto(c.getIdCategoria(), c.getNombreCategoria()))
                .toList();
    }

    @Transactional(readOnly = true)
    public CategoriaDto getById(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada con ID: " + id));
        return new CategoriaDto(categoria.getIdCategoria(), categoria.getNombreCategoria());
    }

    @Transactional
    public CategoriaDto create(CategoriaDto dto) {
        Categoria categoria = new Categoria();
        categoria.setNombreCategoria(dto.getNombreCategoria());
        Categoria saved = categoriaRepository.save(categoria);
        return new CategoriaDto(saved.getIdCategoria(), saved.getNombreCategoria());
    }

    @Transactional
    public CategoriaDto update(Long id, CategoriaDto dto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada con ID: " + id));
        categoria.setNombreCategoria(dto.getNombreCategoria());
        Categoria saved = categoriaRepository.save(categoria);
        return new CategoriaDto(saved.getIdCategoria(), saved.getNombreCategoria());
    }

    @Transactional
    public void delete(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new RuntimeException("Categoria no encontrada con ID: " + id);
        }
        categoriaRepository.deleteById(id);
    }
}
