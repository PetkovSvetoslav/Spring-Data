package springdata.exercises.bookshopsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import springdata.exercises.bookshopsystem.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
