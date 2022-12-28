package szathmary.vai.services.interfaces;

import java.util.List;
import szathmary.vai.entities.Blog;

public interface IBlogService {

  List<Blog> getAllBlogs();

  Blog getBlogById(Integer id);

  Blog updateBlog(Blog blogToUpdate, Integer authorId, Integer pictureId);

  void deleteBlog(Blog blogToDelete);

  Blog createBlog(Blog blogToCreate, Integer authorId, Integer pictureId);

  List<Blog> getBlogsByCategoriesCategoryId(Integer categoryId);
}
