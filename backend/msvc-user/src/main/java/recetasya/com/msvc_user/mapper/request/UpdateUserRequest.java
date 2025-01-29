package recetasya.com.msvc_user.mapper.request;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Data @NoArgsConstructor @AllArgsConstructor
@Schema(description = "Update User Request")
public class UpdateUserRequest {

    @NotNull(message = "The field id cannot be empty")
    private Long id;

    @NotNull(message = "The field name cannot be empty")
    @Schema(description = "User Name", required = true, example = "Juan")
    private String name;

    @NotNull(message = "The field lastname cannot be empty")
    @Schema(description = "User LastName", required = true, example = "Perez")
    private String lastname;

    @NotNull(message = "The field age cannot be empty")
    @Schema(description = "User Age", required = true, example = "25")
    private int age;

    @NotNull(message = "The field username cannot be empty")
    @Schema(description = "User Username", required = true, example = "juanperez")
    private String username;

    @NotNull(message = "The field email cannot be empty")
    @Schema(description = "User Email", required = true, example = "juan@mail")
    private String email;

    @NotNull(message = "The field password cannot be empty")
    @Schema(description = "User Password", required = true, example = "1234")
    private String password;
    
}

