package springdata.exercises.bookshopsystem.init;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import springdata.exercises.bookshopsystem.models.Author;
import springdata.exercises.bookshopsystem.models.Book;
import springdata.exercises.bookshopsystem.models.enums.AgeRestriction;
import springdata.exercises.bookshopsystem.services.AuthorService;
import springdata.exercises.bookshopsystem.services.BookService;
import springdata.exercises.bookshopsystem.services.CategoryService;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
@Slf4j
public class ConsoleRunner implements CommandLineRunner {

    private final AuthorService authorService;
    private final BookService bookService;
    private final CategoryService categoryService;

    private final BufferedReader reader;

    @Autowired
    public ConsoleRunner(AuthorService authorService, BookService bookService, CategoryService categoryService, BufferedReader reader) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.categoryService = categoryService;
        this.reader = reader;
    }

    @Override
    public void run(String... args) throws Exception {
        this.seed();

        System.out.println("Enter the number of the exercise:");

        int exerciseNumber = Integer.parseInt(reader.readLine());

        switch (exerciseNumber) {
            case 1:
                this.booksTitlesByAgeRestriction();
                break;
            case 2:
                this.goldenBooks();
                break;
            case 3:
                this.booksByPrice();
                break;
            case 4:
                this.notReleaseBooks();
                break;
            case 5:
                this.booksReleaseBeforeDate();
                break;
            case 6:
                this.authorSearch();
                break;
            case 7:
                this.bookSearch();
                break;
            case 8:
                this.bookTitlesSearch();
                break;
            case 9:
                this.countBooks();
                break;
            case 10:
                this.totalBookCopies();
                break;
            case 11:
                this.reduceBook();
                break;
            case 12:
                this.increaseBookCopies();
                break;
            case 13:
                this.removeBooks();
                break;
            case 14:
                this.storedProcedure();
                break;
        }
    }
    // 1. Books Titles by Age Restriction
    @SneakyThrows
    private void booksTitlesByAgeRestriction() {
        System.out.println("Enter age restriction [minor, teen, adult]:");

        AgeRestriction ageRestriction = AgeRestriction.valueOf(reader.readLine().toUpperCase(Locale.ROOT));

        this.bookService.findAllBookTitlesByAgeRestriction(ageRestriction)
                .forEach(System.out::println);
    }
    // 2. Golden Books
    private void goldenBooks() {
        this.bookService.findAllGoldBookTitlesWithCopiesLessThan5000()
                .forEach(System.out::println);
    }
    // 3. Books by Price
    private void booksByPrice() {
        this.bookService.findAllBooksWithPriceNotBetween5And40()
                .forEach(System.out::println);
    }
    // 4. Not Release Books
    @SneakyThrows
    private void notReleaseBooks() {
        System.out.println("Enter year:");

        int year = Integer.parseInt(reader.readLine());

        this.bookService.findAllBookTitlesThatHaveNotBeenReleasedInYear(year)
                .forEach(System.out::println);
    }
    // 5. Books Released Before Date
    @SneakyThrows
    private void booksReleaseBeforeDate() {
        String dateFormat = "dd-MM-yyyy";

        System.out.printf("Enter date in format '%s':", dateFormat);

        LocalDate date = LocalDate.parse(reader.readLine(), DateTimeFormatter.ofPattern(dateFormat));

        this.bookService.findAllBooksBeforeDate(date)
                .forEach(b -> System.out.printf("%s {%s} - %.2flv.%n",
                        b.getTitle(),
                        b.getEditionType(),
                        b.getPrice()));
    }
    // 6. Author Search
    @SneakyThrows
    private void authorSearch() {
        System.out.println("Enter the postfix from the first name:");
        String postfix = reader.readLine();

        this.authorService.findAllAuthorNamesWhoseFirstNamesEndsWith(postfix)
                .forEach(System.out::println);
    }
    // 7. Book Search
    @SneakyThrows
    private void bookSearch() {
        System.out.println("Enter a substring:");
        String substring = reader.readLine();

        this.bookService.findAllBookTitlesWitchContains(substring)
                .forEach(System.out::println);
    }
    // 8. Book Titles Search
    @SneakyThrows
    private void bookTitlesSearch() {
        System.out.println("Enter the prefix from the last name:");
        String prefix = reader.readLine();

        this.bookService.findAllBookWithAuthorsWhoseLastnameStartsWith(prefix)
                .forEach(System.out::println);
    }
    // 9. Count Books
    @SneakyThrows
    private void countBooks() {
        System.out.println("Enter title length:");
        int titleLength = Integer.parseInt(reader.readLine());

        long count = this.bookService.countOfBooksWithTitleLengthLongerThan(titleLength);

        System.out.printf("There are %d books with longer title than %d symbols.%n",
                count, titleLength);
    }
    // 10. Total Book Copies
    private void totalBookCopies() {
        this.authorService.findAllAuthorsWithTheirTotalBookCopies()
                .forEach(System.out::println);
    }
    // 11. Reduce Book
    @SneakyThrows
    private void reduceBook() {
        System.out.println("Enter book title:");
        String title = reader.readLine();

        System.out.println(this.bookService.findBookByTitle(title));
    }
    // 12. Increase Book Copies
    @SneakyThrows
    private void increaseBookCopies() {
        String dateFormat = "dd MMM yyyy";
        System.out.println("Enter the date in format '" + dateFormat + "':");
        String dateAsString = reader.readLine();
        LocalDate date = LocalDate.parse(dateAsString, DateTimeFormatter.ofPattern(dateFormat));

        System.out.println("Enter the number of copies:");
        int numberOfCopies = Integer.parseInt(reader.readLine());

        int affectedCount = this.bookService.increaseBookCopiesThatAreReleasedAfterADate(date, numberOfCopies);

        System.out.printf("%d books are released after %s, so total of %d book copies were added.%n",
                numberOfCopies,
                dateAsString,
                numberOfCopies * affectedCount);
    }
    // 13. Remove Books
    @SneakyThrows
    private void removeBooks() {
        System.out.println("Enter number of copies:");
        int numberOfCopies = Integer.parseInt(reader.readLine());

        int deletedBooks = this.bookService.removeBooksWithCopiesLessThan(numberOfCopies);

        System.out.printf("%d books have copies less than %d, so we deleted them.%n",
                deletedBooks,
                numberOfCopies);
    }
    // 14. Stored Procedure
    @SneakyThrows
    private void storedProcedure() {
        System.out.println("Enter the author's first name and last name:");
        String[] names = reader.readLine().split("\\s+");

        String firstName = names[0];
        String lastName = names[1];

        System.out.println(this.bookService.findAllBooksByAuthorNames(firstName, lastName)
                .size());
    }




//    .....................................

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
