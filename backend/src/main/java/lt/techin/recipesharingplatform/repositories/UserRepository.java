package lt.techin.recipesharingplatform.repositories;

import lt.techin.recipesharingplatform.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);

    Optional<User> findUserById(long id);

    boolean existsByDisplayName(String displayName);

    boolean existsByEmail(String email);
}
