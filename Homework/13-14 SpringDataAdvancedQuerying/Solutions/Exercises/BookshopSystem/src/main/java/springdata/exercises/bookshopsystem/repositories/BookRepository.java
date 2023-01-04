package springdata.exercises.bookshopsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import springdata.exercises.bookshopsystem.models.Book;
import springdata.exercises.bookshopsystem.models.enums.AgeRestriction;
import springdata.exercises.bookshopsystem.models.enums.EditionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByReleaseDateAfter(LocalDate releaseDate);

    @Query("SELECT b FROM Book AS b WHERE b.author.id = :authorId")
    List<Book> getBooksByAuthorOrderedByReleaseDateThenByTitle(@Param("authorId") Long authorId);

    List<Book> findAllByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> findAllByEditionTypeAndCopiesBefore(EditionType editionType, int copies);

    List<Book> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal lowerBound, BigDecimal upperBound);

    List<Book> findAllByReleaseDateBeforeOrReleaseDateAfter(LocalDate lowerBound, LocalDate upperBound);

    List<Book> findAllByReleaseDateBefore(LocalDate releaseDate);

    List<Book> findAllByTitleContaining(String substring);

    List<Book> findAllByAuthor_LastNameStartsWith(String prefix);

    @Query("SELECT COUNT(b) FROM Book AS b WHERE LENGTH(b.title) > :length ")
    long countOfBooksWithTitleLengthLongerThan(@Param("length") int length);

    Optional<Book> findBookByTitle(String title);

    @Modifying
    @Query("UPDATE Book AS b SET b.copies = b.copies + :numberOfCopies WHERE b.releaseDate > :date")
    int increaseCopiesOfBooksReleasedAfterADate(@Param("date") LocalDate date,
                                                @Param("numberOfCopies") int numberOfCopies);

    @Modifying
    int deleteAllByCopiesLessThan(int numberOfCopies);

    @Query(value = "CALL find_all_books_by_the_names_of_the_author(:f, :l)", nativeQuery = true)
    List<Book> getBooksByAuthorNames(@Param("f") String firstname, @Param("l") String lastname);
}
