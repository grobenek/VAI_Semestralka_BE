package szathmary.vai.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import szathmary.vai.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

  List<Comment> getCommentsByBlogBlogId(Integer blogId);
}
