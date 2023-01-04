package springdata.exercises.bookshopsystem.services;

import springdata.exercises.bookshopsystem.models.Author;

import java.io.IOException;
import java.util.List;

public interface AuthorService {
    void seedAuthor() throws IOException;

    Author getAuthorByName(String firstName, String lastName);

    Author getRandomAuthor();

    List<Author> findAllAuthorsWhoHaveReleaseBooksBefore(int year);

    List<Author> findAllAuthorsOrderedByBooksCountDesc();
}
