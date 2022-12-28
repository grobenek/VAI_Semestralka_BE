package szathmary.vai.dtos;

import java.sql.Timestamp;
import lombok.Data;

@Data
public class BlogDto {

  private Integer blogId;
  private Integer authorId;
  private Integer pictureId;
  private String title;
  private String text;
  private Timestamp timestamp;
}
