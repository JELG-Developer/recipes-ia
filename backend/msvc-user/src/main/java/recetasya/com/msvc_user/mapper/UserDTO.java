package recetasya.com.msvc_user.mapper;

import lombok.*;
import java.time.LocalDateTime;

@Data
public class UserDTO {

    private Long id;
    private String name;
    private String lastname;
    private int age;
    private String username;
    private String email;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}