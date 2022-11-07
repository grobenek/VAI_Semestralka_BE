package szathmary.vai.controllers;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import szathmary.vai.dtos.UserDto;
import szathmary.vai.entities.User;
import szathmary.vai.services.IUserService;

@Slf4j
@Valid
@RestController
@RequestMapping("/api/user")
public class UserController {

  private final IUserService userService;
  private final ModelMapper modelMapper;

  @Autowired
  public UserController(IUserService userService) {
    this.userService = userService;
    this.modelMapper = new ModelMapper();
  }

  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<List<UserDto>> getAllUsers() {
    HttpHeaders headers = getHttpHeaders();

    List<User> users = this.userService.getAllUsers();
    List<UserDto> usersToReturn = users.stream().map(x -> this.modelMapper.map(x, UserDto.class))
        .collect(Collectors.toList());

    return ResponseEntity.ok().headers(headers).body(usersToReturn);
  }

  @RequestMapping(path = "{id}", method = RequestMethod.GET)
  public ResponseEntity<UserDto> getUserById(
      @PathVariable
      @NotNull(message = "UserId must not be null!")
      @Positive(message = "UserId must be positive number!") Integer id) {
    log.info("getUserById started with id {}", id);
    HttpHeaders headers = getHttpHeaders();

    User user = this.userService.getUserById(id);

    if (user == null) {
      return ResponseEntity.notFound().headers(headers).build();
    }

    UserDto userToReturn = modelMapper.map(user, UserDto.class);

    return ResponseEntity.ok().headers(headers).body(userToReturn);
  }

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<UserDto> createUser(@Valid @RequestBody User userToCreate) {
    HttpHeaders headers = getHttpHeaders();

    User createdUser = this.userService.createUser(userToCreate);

    if (createdUser == null) {
      return ResponseEntity.notFound().headers(headers).build();
    }

    UserDto userDtoToReturn = modelMapper.map(createdUser, UserDto.class);

    return ResponseEntity.ok().headers(headers).body(userDtoToReturn);
  }

  @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
  public ResponseEntity<HttpStatus> deleteUserById(
      @NotNull(message = "UserId must not be null!")
      @Positive(message = "UserId must be positive number!")
      @PathVariable Integer id) {
    HttpHeaders headers = getHttpHeaders();

    User foundUser = this.userService.getUserById(id);

    if (foundUser == null) {
      return ResponseEntity.notFound().headers(headers).build();
    }

    this.userService.deleteUser(foundUser);

    return ResponseEntity.ok().headers(headers).build();
  }

  @RequestMapping(path = "{id}", method = RequestMethod.PUT)
  public ResponseEntity<UserDto> updateUserById(
      @NotNull(message = "UserId must not be null!")
      @Positive(message = "UserId must be positive number!")
      @PathVariable Integer id,
      @Valid @RequestBody User userToUpdate) {
    HttpHeaders headers = getHttpHeaders();

    User foundUser = this.userService.getUserById(id);

    if (foundUser == null) {
      return ResponseEntity.notFound().headers(headers).build();
    }

    BeanUtils.copyProperties(userToUpdate, foundUser, "userId");

    this.userService.updateUser(foundUser);
    UserDto userToReturn = modelMapper.map(foundUser, UserDto.class);

    return ResponseEntity.ok().headers(headers).body(userToReturn);
  }

  private static HttpHeaders getHttpHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.add("UserController", "responded");
    headers.add("Access-Control-Allow-Origin", "*");
    return headers;
  }
}
