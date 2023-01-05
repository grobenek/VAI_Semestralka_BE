package szathmary.vai.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import szathmary.vai.entities.Blog;

public interface BlogRepository extends JpaRepository<Blog, Integer> {

  List<Blog> getBlogsByCategoriesCategoryNameCategoryNameId(Integer categoryNameId);

  List<Blog> getBlogsByAuthorUserId(Integer userId);
}
