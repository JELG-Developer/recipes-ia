package recetasya.com.msvc_user.mapper.request;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Data @NoArgsConstructor @AllArgsConstructor
@Schema(description = "Find Username Request")
public class FindUsernameRequest {

    @NotNull(message = "The field username cannot be empty")
    @Schema(description = "User Username", required = true, example = "juanperez")
    private String username;
}
