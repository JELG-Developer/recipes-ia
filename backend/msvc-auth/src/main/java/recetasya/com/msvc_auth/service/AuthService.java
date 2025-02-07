package recetasya.com.msvc_auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.nimbusds.jose.JOSEException;
import recetasya.com.msvc_auth.mapper.request.AuthRequest;
import recetasya.com.msvc_auth.mapper.request.LogoutRequest;
import recetasya.com.msvc_auth.mapper.request.RefreshTokenRequest;
import recetasya.com.msvc_auth.mapper.response.AuthResponse;
import recetasya.com.msvc_auth.util.JwtUtil;
import java.text.ParseException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final Set<String> revokedTokens = ConcurrentHashMap.newKeySet();

    public AuthResponse authenticate(AuthRequest request) throws JOSEException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        String accessToken = jwtUtil.generateAccessToken(request.getEmail());
        String refreshToken = jwtUtil.generateAccessToken(request.getEmail());

        return new AuthResponse(accessToken, refreshToken);
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

    public void logout(LogoutRequest request) {
        revokedTokens.add(request.getToken());
        revokedTokens.add(request.getRefreshToken());
    }

    public boolean isTokenRevoked(String token) {
        return revokedTokens.contains(token);
    }
}
