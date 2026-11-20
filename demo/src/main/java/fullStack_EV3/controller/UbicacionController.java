package fullStack_EV3.controller;

import fullStack_EV3.dto.ComunaResponseDto;
import fullStack_EV3.dto.RegionResponseDto;
import fullStack_EV3.service.UbicacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ubicaciones")
@Tag(name = "Ubicaciones", description = "Endpoints publicos para regiones y comunas de Chile")
public class UbicacionController {

    private final UbicacionService ubicacionService;

    public UbicacionController(UbicacionService ubicacionService) {
        this.ubicacionService = ubicacionService;
    }

    @GetMapping("/regiones")
    @Operation(summary = "Listar todas las regiones")
    public ResponseEntity<List<RegionResponseDto>> getRegiones() {
        return ResponseEntity.ok(ubicacionService.getRegiones());
    }

    @GetMapping("/regiones/{idRegion}/comunas")
    @Operation(summary = "Listar comunas por ID de region")
    public ResponseEntity<List<ComunaResponseDto>> getComunasPorRegion(@PathVariable Long idRegion) {
        return ResponseEntity.ok(ubicacionService.getComunasPorRegion(idRegion));
    }

    @GetMapping("/comunas")
    @Operation(summary = "Listar todas las comunas")
    public ResponseEntity<List<ComunaResponseDto>> getAllComunas() {
        return ResponseEntity.ok(ubicacionService.getAllComunas());
    }
}
