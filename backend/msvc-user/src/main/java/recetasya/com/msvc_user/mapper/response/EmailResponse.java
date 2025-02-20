package recetasya.com.msvc_user.mapper.response;

import lombok.*;
import lombok.experimental.SuperBuilder;
import recetasya.com.msvc_user.service.entities.User;
import io.swagger.v3.oas.annotations.media.Schema;

@Data @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class EmailResponse {

    @Schema(description = "lastname", example = "Doe", type = "String")
    protected String email;

    @Schema(description = "email", example = "john@mail", type = "String")
    protected String password;

    public static EmailResponse fromUser(User user) {
        return EmailResponse.builder()
            .email(user.getEmail())
            .password(user.getPassword())
            .build();
    }
}
