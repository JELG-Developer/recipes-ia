package recetasya.com.msvc_user.service.entities;

import lombok.*;
import jakarta.persistence.*;

@Entity @Getter @Setter
@Table(name = "RY_USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String lastname;

    private int age;

    private String username;

    private String email;

    private String password;

}
