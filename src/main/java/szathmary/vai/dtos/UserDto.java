package szathmary.vai.dtos;

import lombok.Data;

@Data
public class UserDto {
    public Integer userId;
    public String login;
    public Boolean isAdmin;
}
