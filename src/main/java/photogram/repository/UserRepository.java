package photogram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import photogram.model.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
