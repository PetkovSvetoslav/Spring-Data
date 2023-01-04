package springdata.exercises.bookshopsystem.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springdata.exercises.bookshopsystem.models.Category;
import springdata.exercises.bookshopsystem.repositories.CategoryRepository;
import springdata.exercises.bookshopsystem.services.CategoryService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final String CATEGORIES_FILE_PATH = "src/main/resources/files/categories.txt";
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedCategory() throws IOException {
        Files.readAllLines(Path.of(CATEGORIES_FILE_PATH))
                .stream()
                .filter(row -> !row.isEmpty())
                .forEach(categoryName -> {
                    Category category = new Category(categoryName);

                    this.categoryRepository.save(category);
                });
    }

    @Override
    public Set<Category> getRandomCategories(int count) {
        long categoryRepositoryCount = this.categoryRepository.count();
        if (count > categoryRepositoryCount) {
            throw new IllegalArgumentException(
                    String.format(
                            "The specified count (%d) is greater than the count of categories in the CategoriesRepository (%d).",
                            count, categoryRepositoryCount)
            );
        }

        Set<Category> result = new HashSet<>();
        while (count > result.size()) {
            Long randomId = ThreadLocalRandom.current().nextLong(categoryRepositoryCount) + 1;

            result.add(this.categoryRepository.findById(randomId).get());
        }

        return result;
    }
}
