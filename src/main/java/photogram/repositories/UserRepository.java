package photogram.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import photogram.models.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
