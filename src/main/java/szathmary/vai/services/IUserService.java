package szathmary.vai.services;

import szathmary.vai.entities.User;

import java.util.List;

public interface IUserService {
    List<User> getAllUsers();

    User getUserById(Integer id);

    User updateUser(User userToUpdate);

    void deleteUser(User userToDelete);

    User createUser(User userToCreate);
}
