package fullStack_EV3.service;

import fullStack_EV3.dto.MarcaDto;
import fullStack_EV3.model.Marca;
import fullStack_EV3.repository.MarcaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MarcaService {

    private final MarcaRepository marcaRepository;

    public MarcaService(MarcaRepository marcaRepository) {
        this.marcaRepository = marcaRepository;
    }

    @Transactional(readOnly = true)
    public List<MarcaDto> getAll() {
        return marcaRepository.findAll().stream()
                .map(m -> new MarcaDto(m.getIdMarca(), m.getNombreMarca()))
                .toList();
    }

    @Transactional(readOnly = true)
    public MarcaDto getById(Long id) {
        Marca marca = marcaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Marca no encontrada con ID: " + id));
        return new MarcaDto(marca.getIdMarca(), marca.getNombreMarca());
    }

    @Transactional
    public MarcaDto create(MarcaDto dto) {
        Marca marca = new Marca();
        marca.setNombreMarca(dto.getNombreMarca());
        Marca saved = marcaRepository.save(marca);
        return new MarcaDto(saved.getIdMarca(), saved.getNombreMarca());
    }

    @Transactional
    public MarcaDto update(Long id, MarcaDto dto) {
        Marca marca = marcaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Marca no encontrada con ID: " + id));
        marca.setNombreMarca(dto.getNombreMarca());
        Marca saved = marcaRepository.save(marca);
        return new MarcaDto(saved.getIdMarca(), saved.getNombreMarca());
    }

    @Transactional
    public void delete(Long id) {
        if (!marcaRepository.existsById(id)) {
            throw new RuntimeException("Marca no encontrada con ID: " + id);
        }
        marcaRepository.deleteById(id);
    }
}
