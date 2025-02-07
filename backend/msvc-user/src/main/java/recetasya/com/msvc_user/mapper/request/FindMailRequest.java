package recetasya.com.msvc_user.mapper.request;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Data @NoArgsConstructor @AllArgsConstructor
@Schema(description = "Find Mail Request")
public class FindMailRequest {

    @NotNull(message = "The field mail cannot be empty")
    @Schema(description = "User Mail", required = true, example = "juanperez.mail")
    private String mail;
}
