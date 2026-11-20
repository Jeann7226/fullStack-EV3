package fullStack_EV3.service;

import fullStack_EV3.dto.ComunaResponseDto;
import fullStack_EV3.dto.RegionResponseDto;
import fullStack_EV3.model.Comuna;
import fullStack_EV3.model.Region;
import fullStack_EV3.repository.ComunaRepository;
import fullStack_EV3.repository.RegionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UbicacionService {

    private final RegionRepository regionRepository;
    private final ComunaRepository comunaRepository;

    public UbicacionService(RegionRepository regionRepository, ComunaRepository comunaRepository) {
        this.regionRepository = regionRepository;
        this.comunaRepository = comunaRepository;
    }

    @Transactional(readOnly = true)
    public List<RegionResponseDto> getRegiones() {
        return regionRepository.findAll().stream()
                .map(r -> new RegionResponseDto(r.getIdRegion(), r.getNombreRegion()))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ComunaResponseDto> getComunasPorRegion(Long idRegion) {
        return comunaRepository.findByRegionIdRegion(idRegion).stream()
                .map(c -> new ComunaResponseDto(
                        c.getIdComuna(),
                        c.getNombreComuna(),
                        c.getRegion() != null ? c.getRegion().getIdRegion() : null,
                        c.getRegion() != null ? c.getRegion().getNombreRegion() : null
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ComunaResponseDto> getAllComunas() {
        return comunaRepository.findAll().stream()
                .map(c -> new ComunaResponseDto(
                        c.getIdComuna(),
                        c.getNombreComuna(),
                        c.getRegion() != null ? c.getRegion().getIdRegion() : null,
                        c.getRegion() != null ? c.getRegion().getNombreRegion() : null
                ))
                .toList();
    }
}
