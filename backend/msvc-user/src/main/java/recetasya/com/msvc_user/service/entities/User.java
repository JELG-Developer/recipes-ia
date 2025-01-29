package recetasya.com.msvc_user.service.entities;

import lombok.*;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity @Getter @Setter
@Table(name = "RY_USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 50)
    private String username;

    @Column(length = 50)
    private String name;

    @Column(length = 50)
    private String lastname;

    @Column(length = 3)
    private int age;
    
    @Column(unique = true, length = 50)
    private String email;

    private String password;

    @OneToOne(mappedBy = "user")
    @JsonManagedReference
    private Role role;

}
