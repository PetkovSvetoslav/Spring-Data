package course.springdata.hibernateintro.entity.onlineshop;

import java.util.HashSet;
import java.util.Set;

public class Stock {
    private long id;
    private String code;
    private String name;
    private Set<Category> categories;

    public Stock() {
        this.categories = new HashSet<>();
    }

    public Stock(String name, String code) {
        this();
        this.name = name;
        this.code = code;
    }

    public void addToCategory(Category category) {
        this.categories.add(category);
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Category> getCategories() {
        return this.categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
}