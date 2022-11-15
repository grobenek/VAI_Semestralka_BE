package szathmary.vai.services.implementations;

import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import szathmary.vai.entities.Blog;
import szathmary.vai.entities.Comment;
import szathmary.vai.entities.User;
import szathmary.vai.repositories.BlogRepository;
import szathmary.vai.repositories.CommentRepository;
import szathmary.vai.repositories.UserRepository;
import szathmary.vai.services.interfaces.ICommentService;

@Service
public class CommentServiceImpl implements ICommentService {

  private final CommentRepository commentRepository;
  private final UserRepository userRepository;
  private final BlogRepository blogRepository;

  @Autowired
  public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository,
      BlogRepository blogRepository) {
    this.commentRepository = commentRepository;
    this.userRepository = userRepository;
    this.blogRepository = blogRepository;
  }

  @Override
  public List<Comment> getAllComments() {
    return this.commentRepository.findAll();
  }

  @Override
  public Comment getCommentById(Integer id) {
    return this.commentRepository.getReferenceById(id);
  }

  @Override
  public void updateComment(Comment commentToUpdate) {
    Blog blogToUpdate = blogRepository.getReferenceById(commentToUpdate.getBlog().getBlogId());
    User authorToUpdate = userRepository.getReferenceById(
        commentToUpdate.getAuthor().getUserId());

    commentToUpdate.setBlog(blogToUpdate);
    commentToUpdate.setAuthor(authorToUpdate);

    this.commentRepository.save(commentToUpdate);
  }

  @Override
  public void deleteComment(Comment blogToDelete) {
    this.commentRepository.delete(blogToDelete);
  }

  @Override
  public Comment createComment(Comment blogToCreate) {
    User author = userRepository.getReferenceById(blogToCreate.getAuthor().getUserId());
    Blog blog = blogRepository.getReferenceById(blogToCreate.getBlog().getBlogId());

    blogToCreate.setIsEdited(false);
    blogToCreate.setTimestamp(new Timestamp(System.currentTimeMillis()));
    blogToCreate.setAuthor(author);
    blogToCreate.setBlog(blog);

    return this.commentRepository.save(blogToCreate);
  }
}
