package recetasya.com.msvc_user.mapper.request;

import lombok.*;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Find Id Request")
public class FindIdRequest {

    @NotNull(message = "The field id cannot be empty")
    @Schema(description = "Find Id", required = true, example = "1")
    private Long id;
}

