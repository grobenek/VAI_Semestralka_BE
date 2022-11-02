package szathmary.vai.services;

import java.util.ArrayList;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import szathmary.vai.entities.Blog;
import szathmary.vai.entities.User;
import szathmary.vai.repositories.BlogRepository;

import java.util.List;
import szathmary.vai.repositories.UserRepository;

@Service
public class BlogServiceImpl implements IBlogService {

    private final BlogRepository blogRepository;
    private final UserRepository userRepository;

    @Autowired
    public BlogServiceImpl(BlogRepository blogRepository, UserRepository userRepository) {
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
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
    public Blog updateBlog(Blog blogToUpdate, Integer authorId) {
        Blog existingBlog = this.getBlogById(blogToUpdate.getBlogId());
        User authorToUpdate = userRepository.getReferenceById(authorId);

        BeanUtils.copyProperties(existingBlog, blogToUpdate, "blogId");

        blogToUpdate.setAuthor(authorToUpdate);

        return this.blogRepository.save(blogToUpdate);
    }

    @Override
    public void deleteBlog(Blog blogToDelete) {
        this.blogRepository.delete(blogToDelete);
    }

    @Override
    public Blog createBlog(Blog blogToCreate, Integer authorId) {
        User author = this.userRepository.getReferenceById(authorId);
        blogToCreate.setAuthor(author);

        return this.blogRepository.save(blogToCreate);
    }
}
