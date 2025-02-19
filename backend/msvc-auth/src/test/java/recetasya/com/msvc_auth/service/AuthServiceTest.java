package recetasya.com.msvc_auth.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.nimbusds.jose.JOSEException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import recetasya.com.msvc_auth.mapper.request.AuthRequest;
import recetasya.com.msvc_auth.mapper.response.AuthResponse;
import recetasya.com.msvc_auth.util.JwtUtil;

public class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        authService.maxAttempts = 3; /* Se configura el número máximo de intentos */
        authService.loginAttempts.clear(); /* Se limpian los intentos de inicio de sesión previos */
    }

    @Test
    public void testAuthenticateSuccess() throws JOSEException {
        /* Se crea una solicitud de autenticación con un email y una contraseña válidos */
        AuthRequest request = new AuthRequest("test@example.com", "password");
        
        /* Se simula que la autenticación es exitosa */
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        
        /* Se simula la generación de tokens JWT */
        when(jwtUtil.generateAccessToken(anyString())).thenReturn("accessToken");
        
        /* Se ejecuta el método de autenticación */
        AuthResponse response = authService.authenticate(request);
        
        /* Se verifican los resultados esperados */
        assertNotNull(response); /* La respuesta no debe ser nula */
        assertEquals("accessToken", response.getToken()); /* El token de acceso debe ser "accessToken" */
        assertEquals("accessToken", response.getRefreshToken()); /* El token de actualización debe ser el mismo */
        
        /* Se verifica que los métodos fueron llamados correctamente */
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtil, times(2)).generateAccessToken(anyString()); /* Se llama dos veces (access y refresh token) */
    }

    @Test
    public void testAuthenticateBadCredentials() {
        /* Se crea una solicitud con credenciales incorrectas */
        AuthRequest request = new AuthRequest("test@example.com", "wrongPassword");
        
        /* Se simula que el AuthenticationManager lanza una excepción por credenciales incorrectas */
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));
        
        /* Se verifica que la autenticación lanza una excepción con el mensaje esperado */
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.authenticate(request);
        });
        
        assertEquals("Credenciales incorrectas. Intento 1 de 3", exception.getMessage());
        
        /* Se verifica que el método authenticate se llamó exactamente una vez */
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
    
    @Test
    public void testAuthenticateMaxAttemptsExceeded() {
        /* Se crea una solicitud con credenciales incorrectas */
        AuthRequest request = new AuthRequest("test@example.com", "wrongPassword");
        
        /* Se simula que el usuario ya alcanzó el máximo de intentos fallidos */
        authService.loginAttempts.put(request.getEmail(), 3);
        
        /* Se verifica que el intento de autenticación lanza la excepción esperada */
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.authenticate(request);
        });
        
        assertEquals("Has superado el límite de intentos. Intenta más tarde.", exception.getMessage());
        
        /* Se verifica que el método authenticate NUNCA fue llamado debido al bloqueo */
        verify(authenticationManager, never()).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    public void testIsTokenRevoked() {
        /* Se define un token revocado */
        String token = "revokedToken";
        authService.revokedTokens.add(token);
        
        /* Se llama al método para verificar si el token está revocado */
        boolean isRevoked = authService.isTokenRevoked(token);
        
        /* Se verifica que el token esté efectivamente revocado */
        assertTrue(isRevoked);
    }

    @Test
    public void testIsTokenNotRevoked() {
        /* Se define un token que no ha sido revocado */
        String token = "validToken";
        
        /* Se llama al método para verificar si el token está revocado */
        boolean isRevoked = authService.isTokenRevoked(token);
        
        /* Se verifica que el token NO esté revocado */
        assertFalse(isRevoked);
    }
}
