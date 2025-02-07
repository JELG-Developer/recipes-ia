package recetasya.com.msvc_auth.mapper.request;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class LogoutRequest {

    @Schema(description = "Token", example = "dsfasfasdfiouh2q8734h....")
    private String token;

    @Schema(description = "Refresh Token", example = "dsfasfasdfiouh2q8734h....")
    private String refreshToken;
}
