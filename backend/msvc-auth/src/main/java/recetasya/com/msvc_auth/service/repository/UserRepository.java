package recetasya.com.msvc_auth.service.repository;

import java.util.Optional;
import org.springframework.stereotype.Repository;

import recetasya.com.msvc_auth.service.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
