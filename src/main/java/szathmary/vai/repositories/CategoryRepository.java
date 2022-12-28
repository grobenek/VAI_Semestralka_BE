package szathmary.vai.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import szathmary.vai.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

  List<Category> getCategoriesByBlogBlogId(Integer blogId);
}
