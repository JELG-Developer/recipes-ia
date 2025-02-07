package recetasya.com.msvc_auth.mapper.request;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AuthRequest {
    @Schema(description = "Nombre de usuario", example = "usuario123")
    private String username;
    
    @Schema(description = "Contraseña del usuario", example = "password123")
    private String password;
}
