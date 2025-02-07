package recetasya.com.msvc_auth.mapper.request;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AuthRequest {
    @Schema(description = "Email", example = "usuario123")
    private String email;
    
    @Schema(description = "Password", example = "password123")
    private String password;
}
