package recetasya.com.msvc_auth.mapper.request;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class RefreshTokenRequest {

    @Schema(description = "Refresh Token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI...")
    private String refreshToken;
}
