package recetasya.com.msvc_auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nimbusds.jose.JOSEException;

import recetasya.com.msvc_auth.mapper.request.AuthRequest;
import recetasya.com.msvc_auth.mapper.request.LogoutRequest;
import recetasya.com.msvc_auth.mapper.request.RefreshTokenRequest;
import recetasya.com.msvc_auth.mapper.response.AuthResponse;
import recetasya.com.msvc_auth.mapper.response.StandardResponse;
import recetasya.com.msvc_auth.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth Controller", description = "Manage authentication and token issuance")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "login")
    @ApiResponse(responseCode = "200", description = "login")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) throws JOSEException {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/refresh")
    @Operation(summary = "refresh")
    @ApiResponse(responseCode = "200", description = "refresh")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshTokenRequest request) throws JOSEException, ParseException {
        return ResponseEntity.ok(authService.refresh(request));
    }

    @PostMapping("/logout")
    @Operation(summary = "logout")
    @ApiResponse(responseCode = "200", description = "logout")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    public ResponseEntity<StandardResponse> logout(@RequestBody LogoutRequest request) {
        StandardResponse response = authService.logout(request);
        return ResponseEntity.ok(response);
    }

}
