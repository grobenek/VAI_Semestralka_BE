package szathmary.vai.services;

import java.util.List;
import szathmary.vai.entities.Comment;

public interface ICommentService {

  List<Comment> getAllComments();

  Comment getCommentById(Integer id);

  Comment updateComment(Comment blogToUpdate, Integer authorId, Integer blogId);

  void deleteComment(Comment blogToDelete);

  Comment createComment(Comment blogToCreate, Integer authorId, Integer blogId);
}
