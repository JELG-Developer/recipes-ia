package recetasya.com.msvc_auth.entitites;

import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Getter
@Setter
public class User {
    private Long id;
    private String username;
    private String name;
    private String lastname;
    private int age;
    private String email;
    private String password;
    private Set<Role> roles;
}
