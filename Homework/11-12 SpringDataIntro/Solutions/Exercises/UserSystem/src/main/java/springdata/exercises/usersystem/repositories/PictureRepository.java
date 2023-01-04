package springdata.exercises.usersystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import springdata.exercises.usersystem.models.gallery.Picture;

public interface PictureRepository extends JpaRepository<Picture, Long> {
}
