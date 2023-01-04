package com.example.xmlproductshop.service;

import com.example.xmlproductshop.model.dto.CategoryInfoDto;
import com.example.xmlproductshop.model.dto.seed.CategorySeedDto;
import com.example.xmlproductshop.model.entity.Category;

import java.util.List;
import java.util.Set;

public interface CategoryService {

    void seedCategories(List<CategorySeedDto> categories);

    Set<Category> getRandomCategories(int maxCount);

    List<CategoryInfoDto> findAllCategoriesInfo();
}
