package recetasya.com.msvc_user.service.entities;

import lombok.*;
import jakarta.persistence.*;

@Entity @Getter @Setter
@Table(name = "RY_USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String name;

    @Column(length = 50)
    private String lastname;

    @Column(length = 3)
    private int age;

    @Column(unique = true, length = 50)
    private String username;
    
    @Column(unique = true, length = 50)
    private String email;

    private String password;

}
