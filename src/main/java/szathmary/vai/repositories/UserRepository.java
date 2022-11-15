package szathmary.vai.repositories;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import szathmary.vai.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

  User getUserByEmail(
      @NotNull(message = "User email cannot be null!") @NotBlank(message = "User email cannot be blank!") String email);
}
