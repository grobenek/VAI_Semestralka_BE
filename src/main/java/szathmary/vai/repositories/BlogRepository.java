package szathmary.vai.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import szathmary.vai.entities.Blog;

public interface BlogRepository extends JpaRepository<Blog, Integer> {
}
