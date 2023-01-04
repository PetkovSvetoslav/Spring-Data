package springdata.exercises.bookshopsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import springdata.exercises.bookshopsystem.models.Book;

import java.time.LocalDate;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByReleaseDateAfter(LocalDate releaseDate);

    @Query("SELECT b FROM Book AS b WHERE b.author.id = :authorId")
    List<Book> getBooksByAuthorOrderedByReleaseDateThenByTitle(@Param("authorId") Long authorId);
}
