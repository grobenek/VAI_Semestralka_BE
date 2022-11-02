package szathmary.vai.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import szathmary.vai.entities.Comment;
import szathmary.vai.repositories.CommentRepository;

import java.util.List;

@Service
public class CommentServiceImpl implements ICommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
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
    public Comment updateComment(Comment blogToUpdate) {
        return this.commentRepository.save(blogToUpdate);
    }

    @Override
    public void deleteComment(Comment blogToDelete) {
        this.commentRepository.delete(blogToDelete);
    }

    @Override
    public Comment createComment(Comment blogToCreate) {
        return this.commentRepository.save(blogToCreate);
    }
}
