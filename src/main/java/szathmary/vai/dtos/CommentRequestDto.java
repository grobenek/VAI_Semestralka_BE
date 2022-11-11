package szathmary.vai.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentRequestDto {

  private Integer commentId;

  @NotNull(message = "Comment author cannot be null!")
  private Integer authorId;

  @NotNull(message = "Comment blog cannot be null!")
  private Integer blogId;

  @NotNull(message = "Comment text cannot be null!")
  @NotBlank(message = "Comment text cannot be blank!")
  private String text;
}
