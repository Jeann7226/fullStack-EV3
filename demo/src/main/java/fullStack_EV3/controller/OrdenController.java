package fullStack_EV3.controller;

import fullStack_EV3.dto.CrearOrdenRequestDto;
import fullStack_EV3.dto.OrdenResponseDto;
import fullStack_EV3.service.OrdenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ordenes")
@Tag(name = "Ordenes", description = "Gestion de compras y pedidos (Base transaccional)")
@SecurityRequirement(name = "BearerAuth")
public class OrdenController {

    private final OrdenService ordenService;

    public OrdenController(OrdenService ordenService) {
        this.ordenService = ordenService;
    }

    @GetMapping
    @Operation(summary = "Listar todas las ordenes (ADMIN y VENDEDOR)")
    public ResponseEntity<List<OrdenResponseDto>> getAll() {
        return ResponseEntity.ok(ordenService.getAll());
    }

    @GetMapping("/mis-ordenes")
    @Operation(summary = "Listar ordenes del cliente autenticado (CLIENTE)")
    public ResponseEntity<List<OrdenResponseDto>> getMisOrdenes(Authentication authentication) {
        // TODO: [FULL STACK] Obtener el RUN del usuario autenticado para retornar exactamente sus ordenes
        String email = authentication != null ? authentication.getName() : "";
        return ResponseEntity.ok(ordenService.getAll());
    }

    @GetMapping("/usuario/{run}")
    @Operation(summary = "Listar ordenes por RUN de cliente (ADMIN y VENDEDOR)")
    public ResponseEntity<List<OrdenResponseDto>> getByUserRun(@PathVariable String run) {
        return ResponseEntity.ok(ordenService.getByUserRun(run));
    }

    @PostMapping
    @Operation(summary = "Crear nueva orden de compra (CLIENTE o ADMIN)")
    public ResponseEntity<OrdenResponseDto> create(@Valid @RequestBody CrearOrdenRequestDto request,
                                                  Authentication authentication) {
        String email = authentication != null ? authentication.getName() : null;
        return ResponseEntity.status(HttpStatus.CREATED).body(ordenService.create(request, email));
    }
}
