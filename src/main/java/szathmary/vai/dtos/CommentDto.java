package szathmary.vai.dtos;

import java.sql.Timestamp;
import lombok.Data;

@Data
public class CommentDto {

  private Integer commentId;

  private Integer Author;

  private Timestamp timestamp;

  private Integer blogId;

  private String text;
}
