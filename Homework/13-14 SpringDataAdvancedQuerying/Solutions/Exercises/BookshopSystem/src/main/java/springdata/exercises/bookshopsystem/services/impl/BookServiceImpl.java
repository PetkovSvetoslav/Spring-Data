package springdata.exercises.bookshopsystem.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springdata.exercises.bookshopsystem.models.Author;
import springdata.exercises.bookshopsystem.models.Book;
import springdata.exercises.bookshopsystem.models.Category;
import springdata.exercises.bookshopsystem.models.enums.AgeRestriction;
import springdata.exercises.bookshopsystem.models.enums.EditionType;
import springdata.exercises.bookshopsystem.repositories.BookRepository;
import springdata.exercises.bookshopsystem.services.AuthorService;
import springdata.exercises.bookshopsystem.services.BookService;
import springdata.exercises.bookshopsystem.services.CategoryService;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private static final String BOOKS_FILE_PATH = "src/main/resources/files/books.txt";

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedBooks() throws IOException {
        List<String> strings = Files.readAllLines(Path.of(BOOKS_FILE_PATH));

        for (String row : strings) {
            String[] bookInfo = row.split("\\s+");

            Book book = createBook(bookInfo);

            this.bookRepository.save(book);
        }
    }

    @Override
    public List<Book> findAllBooksAfterYear(int year) {
        return this.bookRepository.findAllByReleaseDateAfter(
                LocalDate.of(year, 12, 31)
        );
    }

    @Override
    public List<Book> getBooksByAuthorOrderedByReleaseDateThenByTitle(Author author) {
        return this.bookRepository.getBooksByAuthorOrderedByReleaseDateThenByTitle(author.getId());
    }

    @Override
    public List<String> findAllBookTitlesByAgeRestriction(AgeRestriction ageRestriction) {
        return this.bookRepository.findAllByAgeRestriction(ageRestriction)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllGoldBookTitlesWithCopiesLessThan5000() {
        return this.bookRepository.findAllByEditionTypeAndCopiesBefore(EditionType.GOLD, 5000)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksWithPriceNotBetween5And40() {
        return this.bookRepository.findAllByPriceLessThanOrPriceGreaterThan(BigDecimal.valueOf(5), BigDecimal.valueOf(40))
                .stream()
                .map(b -> String.format("%s - %.2f", b.getTitle(), b.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBookTitlesThatHaveNotBeenReleasedInYear(int year) {
        LocalDate lowerBound = LocalDate.of(year, 1, 1);
        LocalDate upperBound = LocalDate.of(year, 12, 31);

        return this.bookRepository.findAllByReleaseDateBeforeOrReleaseDateAfter(lowerBound, upperBound)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findAllBooksBeforeDate(LocalDate date) {
        return this.bookRepository.findAllByReleaseDateBefore(date);
    }

    @Override
    public List<String> findAllBookTitlesWitchContains(String substring) {
        return this.bookRepository.findAllByTitleContaining(substring)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBookWithAuthorsWhoseLastnameStartsWith(String prefix) {
        return this.bookRepository.findAllByAuthor_LastNameStartsWith(prefix)
                .stream()
                .map(book ->
                        String.format("%s (%s %s)",
                                book.getTitle(),
                                book.getAuthor().getFirstName(),
                                book.getAuthor().getLastName()))
                .collect(Collectors.toList());
    }

    @Override
    public long countOfBooksWithTitleLengthLongerThan(int titleLength) {
        return this.bookRepository.countOfBooksWithTitleLengthLongerThan(titleLength);
    }

    @Override
    public String findBookByTitle(String title) {
        Book book = this.bookRepository.findBookByTitle(title).orElse(null);

        if (book != null) {
            return String.format("%s {%s} %s -> %.2flv.",
                    book.getTitle(),
                    book.getEditionType(),
                    book.getAgeRestriction(),
                    book.getPrice());
        }

        return "";
    }

    @Transactional
    @Override
    public int increaseBookCopiesThatAreReleasedAfterADate(LocalDate date, int numberOfCopies) {
        if (numberOfCopies < 0) {
            throw new IllegalArgumentException("You cannot increase copies by a negative number.");
        }

        return this.bookRepository.increaseCopiesOfBooksReleasedAfterADate(date, numberOfCopies);
    }

    @Transactional
    @Override
    public int removeBooksWithCopiesLessThan(int numberOfCopies) {
        return this.bookRepository.deleteAllByCopiesLessThan(numberOfCopies);
    }

    @Override
    public List<Book> findAllBooksByAuthorNames(String firstName, String lastName) {
        return this.bookRepository.getBooksByAuthorNames(firstName, lastName);
    }

    private Book createBook(String[] bookInfo) {

        EditionType editionType = EditionType.values()[Integer.parseInt(bookInfo[0])];

        LocalDate releaseDate = LocalDate.parse(bookInfo[1],
                DateTimeFormatter.ofPattern("d/M/yyyy"));

        int copies = Integer.parseInt(bookInfo[2]);

        BigDecimal price = new BigDecimal(bookInfo[3]);

        AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(bookInfo[4])];

        String title = Arrays.stream(bookInfo).skip(5).collect(Collectors.joining(" "));

        Author randomAuthor = this.authorService.getRandomAuthor();

        Set<Category> randomCategories = this.categoryService.getRandomCategories(3);

        return new Book(title, randomAuthor, editionType, price, randomCategories, copies, releaseDate, ageRestriction);
    }
}
