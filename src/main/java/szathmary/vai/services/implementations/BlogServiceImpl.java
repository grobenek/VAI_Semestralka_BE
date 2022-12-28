package szathmary.vai.services.implementations;

import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import szathmary.vai.entities.Blog;
import szathmary.vai.entities.Picture;
import szathmary.vai.entities.User;
import szathmary.vai.repositories.BlogRepository;
import szathmary.vai.repositories.PictureRepository;
import szathmary.vai.repositories.UserRepository;
import szathmary.vai.services.interfaces.IBlogService;

@Service
public class BlogServiceImpl implements IBlogService {

  private final BlogRepository blogRepository;
  private final UserRepository userRepository;

  private final PictureRepository pictureRepository;

  @Autowired
  public BlogServiceImpl(BlogRepository blogRepository, UserRepository userRepository,
      PictureRepository pictureRepository) {
    this.blogRepository = blogRepository;
    this.userRepository = userRepository;
    this.pictureRepository = pictureRepository;
  }

  @Override
  public List<Blog> getAllBlogs() {
    return this.blogRepository.findAll();
  }

  @Override
  public Blog getBlogById(Integer id) {
    return this.blogRepository.getReferenceById(id);
  }

  @Override
  public Blog updateBlog(Blog blogToUpdate, Integer authorId, Integer pictureId) {
    Blog existingBlog = this.getBlogById(blogToUpdate.getBlogId());
    User authorToUpdate = userRepository.getReferenceById(authorId);
    Picture pictureToUpdate = this.pictureRepository.getReferenceById(pictureId);

    BeanUtils.copyProperties(existingBlog, blogToUpdate, "blogId");

    blogToUpdate.setAuthor(authorToUpdate);
    blogToUpdate.setPicture(pictureToUpdate);

    return this.blogRepository.save(blogToUpdate);
  }

  @Override
  public void deleteBlog(Blog blogToDelete) {
    this.blogRepository.delete(blogToDelete);
  }

  @Override
  public Blog createBlog(Blog blogToCreate, Integer authorId, Integer pictureId) {
    User author = this.userRepository.getReferenceById(authorId);
    Picture picture = this.pictureRepository.getReferenceById(pictureId);
    blogToCreate.setAuthor(author);
    blogToCreate.setPicture(picture);

    return this.blogRepository.save(blogToCreate);
  }

  @Override
  public List<Blog> getBlogsByCategoriesCategoryId(Integer categoryId) {
    return this.blogRepository.getBlogsByCategoriesCategoryNameCategoryNameId(categoryId);
  }
}
