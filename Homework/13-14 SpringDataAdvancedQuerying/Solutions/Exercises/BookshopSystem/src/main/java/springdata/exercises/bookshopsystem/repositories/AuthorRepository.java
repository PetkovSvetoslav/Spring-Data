package springdata.exercises.bookshopsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import springdata.exercises.bookshopsystem.models.Author;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("SELECT a FROM Author AS a WHERE a.firstName = :firstName AND a.lastName = :lastName")
    List<Author> findAllByFirstNameAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastname);

    @Query(value = "SELECT a FROM Author AS a JOIN a.books as b WHERE b.releaseDate < :date")
    List<Author> findAllByBooksBeforeReleaseDate(@Param(value = "date") LocalDate date);

    @Query("SELECT a FROM Author AS a ORDER BY SIZE(a.books) DESC")
    List<Author> findAllAuthorsOrderedByBooksCountDesc();

    List<Author> findAllByFirstNameEndingWith(String postfix);

    @Query("SELECT a, SUM(b.copies) FROM Author AS a JOIN a.books AS b GROUP BY a")
    List<Object[]> findAllWithTheirTotalBookCopies();
}
