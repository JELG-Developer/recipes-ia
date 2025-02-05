package recetasya.com.msvc_auth.entitites;

import lombok.*;
import jakarta.persistence.*;

@Entity @Getter @Setter
@Table(name = "RY_USER_ROLE")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String name;

    @Column(length = 200)
    private String description;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
