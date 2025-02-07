package recetasya.com.msvc_user.mapper.response;

import lombok.*;
import lombok.experimental.SuperBuilder;
import io.swagger.v3.oas.annotations.media.Schema;
import recetasya.com.msvc_user.service.entities.User;

@Data @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class UserResponse {

    @Schema(description = "id", example = "1", type = "Long")
    protected Long id;

    @Schema(description = "name", example = "John", type = "String")
    protected String name;

    @Schema(description = "lastname", example = "Doe", type = "String")
    protected String lastname;

    @Schema(description = "age", example = "30", type = "int")
    protected int age;

    @Schema(description = "email", example = "john@mail", type = "String")
    protected String email;    

    public static UserResponse fromUser(User user) {
        return UserResponse.builder()
            .id(user.getId())
            .name(user.getName())
            .lastname(user.getLastname())
            .age(user.getAge())
            .email(user.getEmail())
            .build();
    }
}
