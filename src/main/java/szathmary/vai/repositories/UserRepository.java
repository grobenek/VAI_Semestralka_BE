package szathmary.vai.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import szathmary.vai.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}
