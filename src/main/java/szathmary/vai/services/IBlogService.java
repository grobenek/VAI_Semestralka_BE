package szathmary.vai.services;

import szathmary.vai.entities.Blog;
import szathmary.vai.entities.User;

import java.util.List;

public interface IBlogService {

    List<Blog> getAllBlogs();

    Blog getBlogById(Integer id);

    Blog updateBlog(Blog blogToUpdate);

    void deleteBlog(Blog blogToDelete);
}
