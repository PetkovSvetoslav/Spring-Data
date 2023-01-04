package springdata.exercises.bookshopsystem.services;

import springdata.exercises.bookshopsystem.models.Author;
import springdata.exercises.bookshopsystem.models.Book;

import java.io.IOException;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> findAllBooksAfterYear(int year);

    List<Book> getBooksByAuthorOrderedByReleaseDateThenByTitle(Author author);
}
