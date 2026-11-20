package fullStack_EV3.controller;

import fullStack_EV3.dto.AuthResponseDto;
import fullStack_EV3.dto.LoginRequestDto;
import fullStack_EV3.dto.RegistroRequestDto;
import fullStack_EV3.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Autenticacion", description = "Endpoints para inicio de sesion y registro de clientes")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesion", description = "Autentica al usuario con correo y password devolviendo un token JWT y datos de perfil.")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/registro")
    @Operation(summary = "Registro de nuevo usuario", description = "Registra un usuario con rol CLIENTE por defecto y genera el token JWT.")
    public ResponseEntity<AuthResponseDto> registro(@Valid @RequestBody RegistroRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registro(request));
    }
}
