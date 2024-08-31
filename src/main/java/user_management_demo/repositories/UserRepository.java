package user_management_demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import user_management_demo.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}

