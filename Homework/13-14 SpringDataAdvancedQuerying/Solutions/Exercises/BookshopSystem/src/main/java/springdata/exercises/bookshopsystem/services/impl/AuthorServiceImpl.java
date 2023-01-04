package springdata.exercises.bookshopsystem.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springdata.exercises.bookshopsystem.models.Author;
import springdata.exercises.bookshopsystem.repositories.AuthorRepository;
import springdata.exercises.bookshopsystem.services.AuthorService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private static final String AUTHORS_FILE_PATH = "src/main/resources/files/authors.txt";

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void seedAuthor() throws IOException {
        Files.readAllLines(Path.of(AUTHORS_FILE_PATH))
                .forEach(row -> {
                    String[] fullName = row.split("\\s+");

                    Author author = new Author(fullName[0], fullName[1]);

                    this.authorRepository.save(author);
                });
    }
    @Override
    public Author getAuthorByName(String firstName, String lastName) {
        List<Author> authors = this.authorRepository.findAllByFirstNameAndLastName(firstName, lastName);

        if (authors.isEmpty()) {
            throw new IllegalArgumentException("There are no authors with this name");
        }

        return authors.get(0);
    }

    @Override
    public Author getRandomAuthor() {
        long authorRepositoryCount = this.authorRepository.count();

        if (authorRepositoryCount == 0) {
            throw new IllegalStateException("No authors in the AuthorRepository");
        }

        long randomId = ThreadLocalRandom.current().nextLong(authorRepositoryCount) + 1;

        return authorRepository.findById(randomId).orElseThrow(IllegalStateException::new);
    }

    @Override
    public List<Author> findAllAuthorsWhoHaveReleaseBooksBefore(int year) {
        return this.authorRepository.findAllByBooksBeforeReleaseDate(LocalDate.of(year, 1, 1));
    }

    @Override
    public List<Author> findAllAuthorsOrderedByBooksCountDesc() {
        return this.authorRepository.findAllAuthorsOrderedByBooksCountDesc();
    }

    @Override
    public List<String> findAllAuthorNamesWhoseFirstNamesEndsWith(String postfix) {
        return this.authorRepository.findAllByFirstNameEndingWith(postfix)
                .stream()
                .map(a -> a.getFirstName() + " " + a.getLastName())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllAuthorsWithTheirTotalBookCopies() {
        return this.authorRepository.findAllWithTheirTotalBookCopies()
                .stream()
                .map(o -> String.format("%s %s -> %d copies",
                        ((Author) o[0]).getFirstName(),
                        ((Author) o[0]).getLastName(),
                        (long) o[1]))
                .collect(Collectors.toList());

        /*
        return this.authorRepository.findAll()
                .stream()
                .map(author -> String.format("%s %s -> %d copies",
                                author.getFirstName(),
                                author.getLastName(),
                                author.getBooks()
                                        .stream()
                                        .mapToLong(Book::getCopies)
                                        .sum())
                )
                .collect(Collectors.toList());
        */
    }
}
