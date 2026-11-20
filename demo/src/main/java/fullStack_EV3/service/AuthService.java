package fullStack_EV3.service;

import fullStack_EV3.dto.AuthResponseDto;
import fullStack_EV3.dto.LoginRequestDto;
import fullStack_EV3.dto.RegistroRequestDto;
import fullStack_EV3.model.Comuna;
import fullStack_EV3.model.Rol;
import fullStack_EV3.model.Usuario;
import fullStack_EV3.repository.ComunaRepository;
import fullStack_EV3.repository.RolRepository;
import fullStack_EV3.repository.UsuarioRepository;
import fullStack_EV3.security.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final ComunaRepository comunaRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthService(UsuarioRepository usuarioRepository,
                       RolRepository rolRepository,
                       ComunaRepository comunaRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtUtils jwtUtils) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.comunaRepository = comunaRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    public AuthResponseDto login(LoginRequestDto request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getCorreo(), request.getPassword())
        );

        Usuario usuario = usuarioRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con correo: " + request.getCorreo()));

        String rolNombre = usuario.getRol() != null ? usuario.getRol().getNombreRol().toUpperCase() : "CLIENTE";
        String token = jwtUtils.generateToken(usuario.getCorreo(), rolNombre, usuario.getRun());

        return new AuthResponseDto(
                token,
                usuario.getRun(),
                usuario.getNombre(),
                usuario.getApellidos(),
                usuario.getCorreo(),
                rolNombre
        );
    }

    @Transactional
    public AuthResponseDto registro(RegistroRequestDto request) {
        if (usuarioRepository.existsByRun(request.getRun())) {
            throw new IllegalArgumentException("Ya existe un usuario registrado con el RUN: " + request.getRun());
        }

        if (usuarioRepository.existsByCorreo(request.getCorreo())) {
            throw new IllegalArgumentException("Ya existe un usuario registrado con el correo: " + request.getCorreo());
        }

        Rol rolCliente = rolRepository.findByNombreRol("CLIENTE")
                .orElseGet(() -> rolRepository.save(new Rol("CLIENTE")));

        Comuna comuna = null;
        if (request.getIdComuna() != null) {
            comuna = comunaRepository.findById(request.getIdComuna()).orElse(null);
        }

        Usuario usuario = new Usuario();
        usuario.setRun(request.getRun());
        usuario.setNombre(request.getNombre());
        usuario.setApellidos(request.getApellidos());
        usuario.setCorreo(request.getCorreo().toLowerCase().trim());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setRol(rolCliente);
        usuario.setComuna(comuna);
        usuario.setDireccion(request.getDireccion());
        usuario.setFechaNacimiento(request.getFechaNacimiento());

        usuarioRepository.save(usuario);

        String rolNombre = rolCliente.getNombreRol().toUpperCase();
        String token = jwtUtils.generateToken(usuario.getCorreo(), rolNombre, usuario.getRun());

        return new AuthResponseDto(
                token,
                usuario.getRun(),
                usuario.getNombre(),
                usuario.getApellidos(),
                usuario.getCorreo(),
                rolNombre
        );
    }
}
