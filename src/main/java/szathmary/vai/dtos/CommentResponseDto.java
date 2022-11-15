package szathmary.vai.dtos;

import java.sql.Timestamp;
import lombok.Data;

@Data
public class CommentResponseDto {

  private Integer commentId;
  private Integer authorId;
  private Integer blogId;
  private Timestamp timestamp;
  private Boolean isEdited;
  private String text;
}
