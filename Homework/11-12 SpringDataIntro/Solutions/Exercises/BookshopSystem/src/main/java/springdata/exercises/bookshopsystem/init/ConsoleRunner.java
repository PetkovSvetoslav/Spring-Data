package springdata.exercises.bookshopsystem.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import springdata.exercises.bookshopsystem.models.Author;
import springdata.exercises.bookshopsystem.models.Book;
import springdata.exercises.bookshopsystem.services.AuthorService;
import springdata.exercises.bookshopsystem.services.BookService;
import springdata.exercises.bookshopsystem.services.CategoryService;

import java.io.IOException;

@Component
@Slf4j
public class ConsoleRunner implements CommandLineRunner {

    private final AuthorService authorService;
    private final BookService bookService;
    private final CategoryService categoryService;

    @Autowired
    public ConsoleRunner(AuthorService authorService, BookService bookService, CategoryService categoryService) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.seed();

    }

    private void printAllGeorgeBooks() {
        Author george = this.authorService.getAuthorByName("George", "Powell");

        this.bookService.getBooksByAuthorOrderedByReleaseDateThenByTitle(george)
                .forEach(book -> System.out.printf("%s, %s -> %d copies%n",
                        book.getTitle(),
                        book.getReleaseDate(),
                        book.getCopies()));
    }

    private void printAuthorsOrderedByBookCountDesc() {
        this.authorService.findAllAuthorsOrderedByBooksCountDesc()
                .forEach(a -> System.out.printf("%s %s -> %d%n",
                        a.getFirstName(),
                        a.getLastName(),
                        a.getBooks().size()));
    }

    private void printAuthorsWhoHaveReleaseBooksBefore1990() {
        this.authorService.findAllAuthorsWhoHaveReleaseBooksBefore(1990)
                .forEach(a -> System.out.println(a.getFirstName() + " " + a.getLastName()));
    }

    private void printBookTitlesReleasedAfter2000() {
        this.bookService.findAllBooksAfterYear(2000)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    private void seed() throws IOException {
        this.authorService.seedAuthor();
        this.categoryService.seedCategory();
        this.bookService.seedBooks();
    }
}
