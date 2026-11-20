package fullStack_EV3.service;

import fullStack_EV3.dto.BoletaResponseDto;
import fullStack_EV3.model.Boleta;
import fullStack_EV3.repository.BoletaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoletaService {

    private final BoletaRepository boletaRepository;

    public BoletaService(BoletaRepository boletaRepository) {
        this.boletaRepository = boletaRepository;
    }

    @Transactional(readOnly = true)
    public List<BoletaResponseDto> getAll() {
        return boletaRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public BoletaResponseDto getById(Long id) {
        Boleta boleta = boletaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Boleta no encontrada con ID: " + id));
        return mapToResponse(boleta);
    }

    @Transactional(readOnly = true)
    public BoletaResponseDto getByNumeroBoleta(String numeroBoleta) {
        Boleta b = boletaRepository.findByNumeroBoleta(numeroBoleta)
                .orElseThrow(() -> new RuntimeException("Boleta no encontrada: " + numeroBoleta));
        return mapToResponse(b);
    }

    private BoletaResponseDto mapToResponse(Boleta b) {
        return new BoletaResponseDto(
                b.getIdBoleta(),
                b.getPago() != null ? b.getPago().getIdPago() : null,
                b.getNumeroBoleta(),
                b.getFechaEmision(),
                b.getMontoTotal()
        );
    }
}