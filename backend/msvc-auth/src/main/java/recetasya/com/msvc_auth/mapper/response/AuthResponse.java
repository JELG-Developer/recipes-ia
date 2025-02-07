package recetasya.com.msvc_auth.mapper.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AuthResponse {

    @Schema(description = "Token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI...")
    private String token;

    @Schema(description = "RefresToken", example = "eyJasdfdsIUzI1NiIsInR5cCI...")
    private String refreshToken;

}
