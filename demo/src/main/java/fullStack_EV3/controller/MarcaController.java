package fullStack_EV3.controller;

import fullStack_EV3.dto.MarcaDto;
import fullStack_EV3.service.MarcaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/marcas")
@Tag(name = "Marcas", description = "Gestion de marcas (Lectura publica, mutacion para ADMIN)")
public class MarcaController {

    private final MarcaService marcaService;

    public MarcaController(MarcaService marcaService) {
        this.marcaService = marcaService;
    }

    @GetMapping
    @Operation(summary = "Listar todas las marcas (Publico)")
    public ResponseEntity<List<MarcaDto>> getAll() {
        return ResponseEntity.ok(marcaService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener marca por ID (Publico)")
    public ResponseEntity<MarcaDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(marcaService.getById(id));
    }

    @PostMapping
    @SecurityRequirement(name = "BearerAuth")
    @Operation(summary = "Crear marca (ADMIN)")
    public ResponseEntity<MarcaDto> create(@Valid @RequestBody MarcaDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(marcaService.create(dto));
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "BearerAuth")
    @Operation(summary = "Actualizar marca (ADMIN)")
    public ResponseEntity<MarcaDto> update(@PathVariable Long id, @Valid @RequestBody MarcaDto dto) {
        return ResponseEntity.ok(marcaService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "BearerAuth")
    @Operation(summary = "Eliminar marca (ADMIN)")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        marcaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
