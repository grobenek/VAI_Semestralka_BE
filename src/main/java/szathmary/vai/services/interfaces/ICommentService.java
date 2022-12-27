package szathmary.vai.services.interfaces;

import java.util.List;
import szathmary.vai.entities.Comment;

public interface ICommentService {

  List<Comment> getAllComments();

  Comment getCommentById(Integer id);

  void updateComment(Comment blogToUpdate);

  void deleteComment(Comment blogToDelete);

  List<Comment> getAllCommentsByBlogId(Integer id);

  Comment createComment(Comment blogToCreate);
}
