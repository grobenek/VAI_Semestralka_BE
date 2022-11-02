package szathmary.vai.services;

import java.util.List;
import szathmary.vai.entities.Blog;

public interface IBlogService {

  List<Blog> getAllBlogs();

  Blog getBlogById(Integer id);

  Blog updateBlog(Blog blogToUpdate, Integer authorId);

  void deleteBlog(Blog blogToDelete);

  Blog createBlog(Blog blogToCreate, Integer authorId);
}
