package fullStack_EV3.service;

import fullStack_EV3.dto.UsuarioRequestDto;
import fullStack_EV3.dto.UsuarioResponseDto;
import fullStack_EV3.model.Comuna;
import fullStack_EV3.model.Rol;
import fullStack_EV3.model.Usuario;
import fullStack_EV3.repository.ComunaRepository;
import fullStack_EV3.repository.RolRepository;
import fullStack_EV3.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final ComunaRepository comunaRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          RolRepository rolRepository,
                          ComunaRepository comunaRepository,
                          PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.comunaRepository = comunaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponseDto> getAll() {
        return usuarioRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDto getByRun(String run) {
        Usuario usuario = usuarioRepository.findById(run)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con RUN: " + run));
        return mapToResponse(usuario);
    }

    @Transactional
    public UsuarioResponseDto create(UsuarioRequestDto dto) {
        if (usuarioRepository.existsByRun(dto.getRun())) {
            throw new IllegalArgumentException("Ya existe un usuario con RUN: " + dto.getRun());
        }
        if (usuarioRepository.existsByCorreo(dto.getCorreo())) {
            throw new IllegalArgumentException("Ya existe un usuario con correo: " + dto.getCorreo());
        }

        String rolNombre = (dto.getNombreRol() != null && !dto.getNombreRol().isBlank())
                ? dto.getNombreRol().toUpperCase().trim()
                : "CLIENTE";

        Rol rol = rolRepository.findByNombreRol(rolNombre)
                .orElseGet(() -> rolRepository.save(new Rol(rolNombre)));

        Comuna comuna = null;
        if (dto.getIdComuna() != null) {
            comuna = comunaRepository.findById(dto.getIdComuna()).orElse(null);
        }

        Usuario usuario = new Usuario();
        usuario.setRun(dto.getRun());
        usuario.setNombre(dto.getNombre());
        usuario.setApellidos(dto.getApellidos());
        usuario.setCorreo(dto.getCorreo().toLowerCase().trim());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword() != null ? dto.getPassword() : "123456"));
        usuario.setRol(rol);
        usuario.setComuna(comuna);
        usuario.setFechaNacimiento(dto.getFechaNacimiento());
        usuario.setDireccion(dto.getDireccion());

        return mapToResponse(usuarioRepository.save(usuario));
    }

    @Transactional
    public UsuarioResponseDto update(String run, UsuarioRequestDto dto) {
        Usuario usuario = usuarioRepository.findById(run)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con RUN: " + run));

        usuario.setNombre(dto.getNombre());
        usuario.setApellidos(dto.getApellidos());
        usuario.setDireccion(dto.getDireccion());
        usuario.setFechaNacimiento(dto.getFechaNacimiento());

        if (dto.getCorreo() != null && !dto.getCorreo().equalsIgnoreCase(usuario.getCorreo())) {
            if (usuarioRepository.existsByCorreo(dto.getCorreo())) {
                throw new IllegalArgumentException("El correo ya esta en uso por otro usuario");
            }
            usuario.setCorreo(dto.getCorreo().toLowerCase().trim());
        }

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        if (dto.getNombreRol() != null && !dto.getNombreRol().isBlank()) {
            Rol rol = rolRepository.findByNombreRol(dto.getNombreRol().toUpperCase().trim())
                    .orElseGet(() -> rolRepository.save(new Rol(dto.getNombreRol().toUpperCase().trim())));
            usuario.setRol(rol);
        }

        if (dto.getIdComuna() != null) {
            Comuna comuna = comunaRepository.findById(dto.getIdComuna()).orElse(null);
            usuario.setComuna(comuna);
        }

        return mapToResponse(usuarioRepository.save(usuario));
    }

    @Transactional
    public void delete(String run) {
        if (!usuarioRepository.existsById(run)) {
            throw new RuntimeException("Usuario no encontrado con RUN: " + run);
        }
        usuarioRepository.deleteById(run);
    }

    private UsuarioResponseDto mapToResponse(Usuario u) {
        Long idComuna = (u.getComuna() != null) ? u.getComuna().getIdComuna() : null;
        String nombreComuna = (u.getComuna() != null) ? u.getComuna().getNombreComuna() : null;
        String nombreRegion = (u.getComuna() != null && u.getComuna().getRegion() != null)
                ? u.getComuna().getRegion().getNombreRegion()
                : null;
        String nombreRol = (u.getRol() != null) ? u.getRol().getNombreRol() : "CLIENTE";

        return new UsuarioResponseDto(
                u.getRun(),
                u.getNombre(),
                u.getApellidos(),
                u.getCorreo(),
                u.getFechaNacimiento(),
                u.getDireccion(),
                idComuna,
                nombreComuna,
                nombreRegion,
                nombreRol
        );
    }
}
