package springdata.exercises.bookshopsystem.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
