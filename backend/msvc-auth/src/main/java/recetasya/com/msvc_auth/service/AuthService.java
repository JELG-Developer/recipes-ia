package recetasya.com.msvc_auth.service;

import lombok.RequiredArgsConstructor;

import java.text.ParseException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.JOSEException;

import recetasya.com.msvc_auth.mapper.request.AuthRequest;
import recetasya.com.msvc_auth.mapper.request.RefreshTokenRequest;
import recetasya.com.msvc_auth.mapper.response.AuthResponse;
import recetasya.com.msvc_auth.util.JwtUtil;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public AuthResponse authenticate(AuthRequest request) throws JOSEException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        String accessToken = jwtUtil.generateAccessToken(request.getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(request.getUsername());
        return new AuthResponse(accessToken, refreshToken);
    }

    public AuthResponse refresh(RefreshTokenRequest request) throws JOSEException, ParseException {

        if (!jwtUtil.validateToken(request.getRefreshToken())) {
            throw new RuntimeException("Token inválido");
        }

        String username = jwtUtil.getUsernameFromToken(request.getRefreshToken());
        String accessToken = jwtUtil.generateAccessToken(username);
        String refreshToken = jwtUtil.generateRefreshToken(username);

        return new AuthResponse(accessToken, refreshToken);
    }
}
