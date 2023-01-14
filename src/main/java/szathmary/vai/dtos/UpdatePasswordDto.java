package szathmary.vai.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdatePasswordDto {

  @Size(min = 60, message = "User password must be at least 60 characters long!")
  @NotBlank(message = "User password cannot be blank!")
  @NotNull(message = "User password cannot be null!")
  String password;
}
