package recetasya.com.msvc_auth.service.entities;

import lombok.*;
import jakarta.persistence.*;
import java.util.Set;

@Entity
@Getter @Setter
@Table(name = "RY_ROLE")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, unique = true, nullable = false)
    private String name;

    @Column(length = 200)
    private String description;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
}
