package szathmary.vai.repositories;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import szathmary.vai.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

  User getUserByLoginAndPassword(
      @NotNull(message = "User login cannot be null!") @NotBlank(message = "User login cannot be blank!") String login,
      @NotNull(message = "User password cannot be null!") @NotBlank(message = "User password cannot be blank!") String password);
}
