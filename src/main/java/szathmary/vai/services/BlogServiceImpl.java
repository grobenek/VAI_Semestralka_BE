package szathmary.vai.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import szathmary.vai.entities.Blog;
import szathmary.vai.repositories.BlogRepository;

import java.util.List;

@Service
public class BlogServiceImpl implements IBlogService {

    private final BlogRepository blogRepository;

    @Autowired
    public BlogServiceImpl(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
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
    public Blog updateBlog(Blog blogToUpdate) {
        Blog existingBlog = this.getBlogById(blogToUpdate.getBlogId());

        BeanUtils.copyProperties(existingBlog, blogToUpdate, "blogId");

        return this.blogRepository.save(blogToUpdate);
    }

    @Override
    public void deleteBlog(Blog blogToDelete) {
        this.blogRepository.delete(blogToDelete);
    }

    @Override
    public Blog createBlog(Blog blogToCreate) {
        return this.blogRepository.save(blogToCreate);
    }
}
