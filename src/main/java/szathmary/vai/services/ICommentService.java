package szathmary.vai.services;

import szathmary.vai.entities.Comment;

import java.util.List;

public interface ICommentService {

    List<Comment> getAllComments();

    Comment getCommentById(Integer id);

    Comment updateComment(Comment blogToUpdate);

    void deleteComment(Comment blogToDelete);

    Comment createComment(Comment blogToCreate);
}
