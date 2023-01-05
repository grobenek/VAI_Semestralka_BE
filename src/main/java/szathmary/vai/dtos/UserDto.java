package szathmary.vai.dtos;

import lombok.Data;

@Data
public class UserDto {

  private Integer userId;
  private String login;
  private Boolean isAdmin;
  private String email;
  private String aboutUser;
}
