package szathmary.vai.services.implementations;

import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import szathmary.vai.entities.User;
import szathmary.vai.repositories.UserRepository;
import szathmary.vai.services.interfaces.IUserService;

@Service
public class UserServiceImpl implements IUserService {

  private final UserRepository userRepository;

  @Autowired
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public List<User> getAllUsers() {
    return this.userRepository.findAll();
  }

  @Override
  public User getUserById(Integer id) {
    return this.userRepository.getReferenceById(id);
  }

  @Override
  public User updateUser(User userToUpdate) {
    User existingUser = this.getUserById(userToUpdate.getUserId());

    BeanUtils.copyProperties(existingUser, userToUpdate, "userId");

    return this.userRepository.save(userToUpdate);
  }

  @Override
  public void deleteUser(User userToDelete) {
    this.userRepository.delete(userToDelete);
  }

  @Override
  public User createUser(User userToCreate) {
    return this.userRepository.save(userToCreate);
  }

  @Override
  public User getUserByEmail(String email) {
    return this.userRepository.getUserByEmail(email);
  }
}
