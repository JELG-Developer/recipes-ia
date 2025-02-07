package recetasya.com.msvc_auth.entitites;

import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Getter
@Setter
public class Role {
    private Long id;
    private String name;
    private String description;
    private Set<User> users;
}
