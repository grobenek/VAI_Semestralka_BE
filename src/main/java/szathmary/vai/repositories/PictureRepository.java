package szathmary.vai.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import szathmary.vai.entities.Picture;

public interface PictureRepository extends JpaRepository<Picture, Integer> {

}
