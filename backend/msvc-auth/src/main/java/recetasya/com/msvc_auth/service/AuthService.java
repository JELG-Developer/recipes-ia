package recetasya.com.msvc_auth.service;

import java.text.ParseException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.beans.factory.annotation.Value;
import recetasya.com.msvc_auth.mapper.request.AuthRequest;
import recetasya.com.msvc_auth.mapper.request.LogoutRequest;
import recetasya.com.msvc_auth.mapper.request.RefreshTokenRequest;
import recetasya.com.msvc_auth.mapper.response.AuthResponse;
import recetasya.com.msvc_auth.mapper.response.StandardResponse;
import recetasya.com.msvc_auth.util.JwtUtil;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final Set<String> revokedTokens = ConcurrentHashMap.newKeySet();
    private final ConcurrentHashMap<String, Integer> loginAttempts = new ConcurrentHashMap<>();

    @Value("${login.attempts.limit}")
    private int maxAttempts;

    public AuthResponse authenticate(AuthRequest request) throws JOSEException {
        String email = request.getEmail();

        if (loginAttempts.getOrDefault(email, 0) >= maxAttempts) {
            throw new RuntimeException("Has superado el límite de intentos. Intenta más tarde.");
        }

        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, request.getPassword())
            );

            loginAttempts.remove(email);

            String accessToken = jwtUtil.generateAccessToken(email);
            String refreshToken = jwtUtil.generateAccessToken(email);

            return new AuthResponse(accessToken, refreshToken);
        } catch (BadCredentialsException e) {
            loginAttempts.put(email, loginAttempts.getOrDefault(email, 0) + 1);
            throw new RuntimeException("Credenciales incorrectas. Intento " + loginAttempts.get(email) + " de " + maxAttempts);
        }
    }

    public AuthResponse refresh(RefreshTokenRequest request) throws JOSEException, ParseException {
        if (!jwtUtil.validateToken(request.getRefreshToken()) || isTokenRevoked(request.getRefreshToken())) {
            throw new RuntimeException("Token inválido");
        }

        String username = jwtUtil.getUsernameFromToken(request.getRefreshToken());
        String accessToken = jwtUtil.generateAccessToken(username);
        String refreshToken = jwtUtil.generateAccessToken(username);

        return new AuthResponse(accessToken, refreshToken);
    }

    public StandardResponse logout(LogoutRequest request) {
        revokedTokens.add(request.getToken());
        revokedTokens.add(request.getRefreshToken());
        return new StandardResponse(200, "Sesión cerrada correctamente.");
    }

    public boolean isTokenRevoked(String token) {
        return revokedTokens.contains(token);
    }
}
