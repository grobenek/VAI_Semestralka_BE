package szathmary.vai.services.interfaces;

import java.util.List;
import szathmary.vai.entities.User;

public interface IUserService {

  List<User> getAllUsers();

  User getUserById(Integer id);

  User updateUser(User userToUpdate);

  void deleteUser(User userToDelete);

  User createUser(User userToCreate);

  User getUserByEmail(String email);

  void updatePassword(String password, Integer id);
}
