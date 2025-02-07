package recetasya.com.msvc_auth.controller;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.text.ParseException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nimbusds.jose.JOSEException;

import recetasya.com.msvc_auth.mapper.request.AuthRequest;
import recetasya.com.msvc_auth.mapper.request.LogoutRequest;
import recetasya.com.msvc_auth.mapper.request.RefreshTokenRequest;
import recetasya.com.msvc_auth.mapper.response.AuthResponse;
import recetasya.com.msvc_auth.service.AuthService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth Controller", description = "Manejo de autenticación y emisión de tokens")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) throws JOSEException {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshTokenRequest request) throws JOSEException, ParseException {
        return ResponseEntity.ok(authService.refresh(request));
    }

    @PostMapping("/logout")
    public void logout(@RequestBody LogoutRequest request) {
        authService.logout(request);
    }

}
