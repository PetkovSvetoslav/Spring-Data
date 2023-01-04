package springdata.exercises.bookshopsystem.services;

import springdata.exercises.bookshopsystem.models.Category;

import java.io.IOException;
import java.util.Set;

public interface CategoryService {
    void seedCategory() throws IOException;

    Set<Category> getRandomCategories(int count);
}
