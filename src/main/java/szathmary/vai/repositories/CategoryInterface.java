package szathmary.vai.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import szathmary.vai.entities.Category;

public interface CategoryInterface extends JpaRepository<Category, Integer> {

}
