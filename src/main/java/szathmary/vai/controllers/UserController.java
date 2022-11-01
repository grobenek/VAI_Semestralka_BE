package szathmary.vai.controllers;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import szathmary.vai.dtos.UserDto;
import szathmary.vai.entities.User;
import szathmary.vai.services.IUserService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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
        HttpHeaders headers = new HttpHeaders();
        headers.add("UserController", "responded");
        headers.add("Access-Control-Allow-Origin", "*");

        List<User> users = this.userService.getAllUsers();
        List<UserDto> usersToReturn = users.stream().map(x -> this.modelMapper.map(x, UserDto.class)).collect(Collectors.toList());

        return ResponseEntity.ok().headers(headers).body(usersToReturn);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public ResponseEntity<UserDto> getUserById(@PathVariable Integer id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("UserController", "responded");
        headers.add("Access-Control-Allow-Origin", "*");

        User user = this.userService.getUserById(id);

        if (user == null) {
            return ResponseEntity.notFound().headers(headers).build();
        }

        UserDto userToReturn = modelMapper.map(user, UserDto.class);

        return ResponseEntity.ok().headers(headers).body(userToReturn);
    }

}
