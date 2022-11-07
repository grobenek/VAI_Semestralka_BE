package szathmary.vai.dtos;

import lombok.Data;
import szathmary.vai.entities.Blog;
import szathmary.vai.entities.CategoryName;

@Data
public class CategoryDto {

  private Integer categoryId;

  private Blog blog;

  private CategoryName categoryName;

}
