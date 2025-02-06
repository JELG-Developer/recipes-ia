package recetasya.com.msvc_auth.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import recetasya.com.msvc_auth.entitites.User;

import io.micrometer.tracing.Tracer;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private WebClient client;

    @Autowired
    private Tracer tracer;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        tracer.currentSpan().tag("auth.start", "Iniciando autenticación para usuario: " + username);

        try {
            User user = client.post()
                    .uri("/find/username")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue("{\"username\": \"" + username + "\"}")
                    .retrieve()
                    .bodyToMono(User.class)
                    .block();

            List<GrantedAuthority> roles = user.getRoles()
                    .stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toList());

            tracer.currentSpan().tag("auth.success", "Autenticación exitosa para usuario: " + username);

            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    true,
                    true,
                    true,
                    true,
                    roles);

        } catch (WebClientResponseException e) {
            String error = "Error en el login: usuario '" + username + "' no encontrado";
            tracer.currentSpan().tag("auth.error", error + " - " + e.getMessage());
            throw new UsernameNotFoundException(error);
        }
    }
}
