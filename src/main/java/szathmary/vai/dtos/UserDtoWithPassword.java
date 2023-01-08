package szathmary.vai.dtos;

import lombok.Data;

@Data
public class UserDtoWithPassword {

  private String password;
  private Integer userId;
  private Boolean isAdmin;

}
