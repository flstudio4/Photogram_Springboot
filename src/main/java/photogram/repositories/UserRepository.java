package photogram.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import photogram.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}

