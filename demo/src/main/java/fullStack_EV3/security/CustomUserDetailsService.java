package fullStack_EV3.security;

import fullStack_EV3.model.Usuario;
import fullStack_EV3.repository.UsuarioRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreo(username)
                .or(() -> usuarioRepository.findById(username))
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con correo o RUN: " + username));

        String nombreRol = usuario.getRol() != null ? usuario.getRol().getNombreRol().toUpperCase().trim() : "CLIENTE";
        if (!nombreRol.startsWith("ROLE_")) {
            nombreRol = "ROLE_" + nombreRol;
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(nombreRol));

        return new User(usuario.getCorreo(), usuario.getPassword(), authorities);
    }
}
