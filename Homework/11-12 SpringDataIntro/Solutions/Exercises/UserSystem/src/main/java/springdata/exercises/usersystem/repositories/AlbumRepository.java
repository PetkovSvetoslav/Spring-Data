package springdata.exercises.usersystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import springdata.exercises.usersystem.models.gallery.Album;

public interface AlbumRepository extends JpaRepository<Album, Long> {
}
