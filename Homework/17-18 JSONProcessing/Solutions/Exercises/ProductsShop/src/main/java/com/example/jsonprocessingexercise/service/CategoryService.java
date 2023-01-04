package com.example.jsonprocessingexercise.service;

import com.example.jsonprocessingexercise.model.dto.CategoryInfoDto;
import com.example.jsonprocessingexercise.model.entity.Category;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface CategoryService {

    void seedCategories() throws IOException;

    Set<Category> getRandomCategories(int maxCount);

    List<CategoryInfoDto> findAllCategories();
}
