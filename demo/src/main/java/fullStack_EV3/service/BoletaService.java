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

    // TODO: [FULL STACK] Agregar metodo para consultar la boleta correspondiente a una orden o idPago,
    // y permitir la descarga o visualizacion en formato PDF o JSON detallado.

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
