package fullStack_EV3.controller;

import fullStack_EV3.dto.BoletaResponseDto;
import fullStack_EV3.service.BoletaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/boletas")
@Tag(name = "Boletas", description = "Consulta de boletas emitidas por compras (ADMIN y VENDEDOR)")
@SecurityRequirement(name = "BearerAuth")
public class BoletaController {

    private final BoletaService boletaService;

    public BoletaController(BoletaService boletaService) {
        this.boletaService = boletaService;
    }

    @GetMapping
    @Operation(summary = "Listar todas las boletas emitidas (ADMIN y VENDEDOR)")
    public ResponseEntity<List<BoletaResponseDto>> getAll() {
        return ResponseEntity.ok(boletaService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener boleta por ID")
    public ResponseEntity<BoletaResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(boletaService.getById(id));
    }

    @GetMapping("/numero/{numeroBoleta}")
    @Operation(summary = "Obtener boleta por numero de folio (ej. BOL-...)")
    public ResponseEntity<BoletaResponseDto> getByNumeroBoleta(@PathVariable String numeroBoleta) {
        return ResponseEntity.ok(boletaService.getByNumeroBoleta(numeroBoleta));
    }
}