package szathmary.vai.dtos;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class BlogDto {
    private Integer blogId;
    private Integer authorId;
    private String text;
    private Timestamp timestamp;
}
