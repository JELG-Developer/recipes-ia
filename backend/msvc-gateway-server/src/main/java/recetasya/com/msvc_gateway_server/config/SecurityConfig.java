package recetasya.com.msvc_gateway_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ClientRegistrations;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.stream.Collectors;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityWebFilterChain(HttpSecurity http) throws Exception {

        return http.authorizeHttpRequests(authz -> {
            // Permitimos el acceso a las rutas de login, refreshToken y logout sin autenticación
            authz.requestMatchers("/login", "/refreshToken", "/logout").permitAll()
                    // Comentamos las rutas de otros recursos que no son necesarias por ahora
                    // .requestMatchers(HttpMethod.GET, "/api/items", "/api/products", "/api/users").permitAll()
                    // .requestMatchers(HttpMethod.GET, "/api/items/{id}", "/api/products/{id}", "/api/users/{id}")
                    // .hasAnyRole("ADMIN", "USER")
                    // .requestMatchers("/api/items/**", "/api/products/**", "/api/users/**").hasRole("ADMIN")
                    // .anyRequest().authenticated();
                    .anyRequest().permitAll();  // Asegúrate de permitir todo lo demás sin restricciones
        })
        .cors(csrf -> csrf.disable())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .oauth2Login(login -> login.loginPage("/oauth2/authorization/client-app"))  // Ruta de login OAuth2
        .oauth2Client()
        .and()
        .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())))
        .build();
    }

    // Método para la conversión de JWT
    private Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter() {
        return source -> {
            Collection<String> roles = source.getClaimAsStringList("roles");
            Collection<GrantedAuthority> authorities = roles.stream()
                    .map(role -> new SimpleGrantedAuthority(role))
                    .collect(Collectors.toList());

            return new JwtAuthenticationToken(source, authorities);
        };
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        ClientRegistration registration = ClientRegistrations
                .fromIssuerLocation("http://127.0.0.1:9100")
                .clientId("gateway-app")
                .clientSecret("12345")
                .authorizationUri("authorization_code")
                .redirectUri("http://127.0.0.1:8090/authorized")
                .scope("openid", "profile")
                .build();

        return new InMemoryClientRegistrationRepository(registration);
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return JwtDecoders.fromIssuerLocation("http://127.0.0.1:9100");
    }
}
