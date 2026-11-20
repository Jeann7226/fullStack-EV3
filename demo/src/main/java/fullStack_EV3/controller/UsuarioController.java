package fullStack_EV3.controller;

import fullStack_EV3.dto.UsuarioRequestDto;
import fullStack_EV3.dto.UsuarioResponseDto;
import fullStack_EV3.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
@Tag(name = "Usuarios", description = "CRUD de usuarios administrativo (exclusivo para rol ADMIN)")
@SecurityRequirement(name = "BearerAuth")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    @Operation(summary = "Listar todos los usuarios (ADMIN)")
    public ResponseEntity<List<UsuarioResponseDto>> getAll() {
        return ResponseEntity.ok(usuarioService.getAll());
    }

    @GetMapping("/{run}")
    @Operation(summary = "Obtener usuario por RUN (ADMIN)")
    public ResponseEntity<UsuarioResponseDto> getByRun(@PathVariable String run) {
        return ResponseEntity.ok(usuarioService.getByRun(run));
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo usuario con rol especifico (ADMIN)")
    public ResponseEntity<UsuarioResponseDto> create(@Valid @RequestBody UsuarioRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.create(dto));
    }

    @PutMapping("/{run}")
    @Operation(summary = "Actualizar datos de usuario (ADMIN)")
    public ResponseEntity<UsuarioResponseDto> update(@PathVariable String run, @Valid @RequestBody UsuarioRequestDto dto) {
        return ResponseEntity.ok(usuarioService.update(run, dto));
    }

    @DeleteMapping("/{run}")
    @Operation(summary = "Eliminar usuario por RUN (ADMIN)")
    public ResponseEntity<Void> delete(@PathVariable String run) {
        usuarioService.delete(run);
        return ResponseEntity.noContent().build();
    }
}
