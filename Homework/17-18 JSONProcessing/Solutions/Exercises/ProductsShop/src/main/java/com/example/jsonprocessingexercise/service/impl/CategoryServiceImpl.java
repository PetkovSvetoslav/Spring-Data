package com.example.jsonprocessingexercise.service.impl;

import com.example.jsonprocessingexercise.model.dto.CategoryInfoDto;
import com.example.jsonprocessingexercise.model.dto.CategorySeedDto;
import com.example.jsonprocessingexercise.model.entity.Category;
import com.example.jsonprocessingexercise.repository.CategoryRepository;
import com.example.jsonprocessingexercise.service.CategoryService;
import com.example.jsonprocessingexercise.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.example.jsonprocessingexercise.constant.GlobalConstant.RESOURCES_FILE_PATH;

@Service
public class CategoryServiceImpl implements CategoryService {

    public static final String CATEGORIES_FILE_NAME = "categories.json";

    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper mapper,
                               ValidationUtil validationUtil, Gson gson) {

        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public void seedCategories() throws IOException {
        String fileContent = Files.readString(Path.of(RESOURCES_FILE_PATH + CATEGORIES_FILE_NAME));

        CategorySeedDto[] categorySeedDtos = this.gson.fromJson(fileContent, CategorySeedDto[].class);

        Arrays.stream(categorySeedDtos)
                .filter(this.validationUtil::isValid)
                .map(categorySeedDto -> this.mapper.map(categorySeedDto, Category.class))
                .forEach(this.categoryRepository::save);
    }

    @Override
    public Set<Category> getRandomCategories(int maxCount) {
        Set<Category> categories = new HashSet<>();
        int size = ThreadLocalRandom.current().nextInt(1, maxCount + 1);
        long totalCategoriesCount = this.categoryRepository.count();

        for (int i = 0; i < size; i++) {
            long randomId = ThreadLocalRandom
                    .current()
                    .nextLong(1, totalCategoriesCount + 1);

            categories.add(this.categoryRepository.findById(randomId).orElse(null));
        }

        return categories;
    }

    @Override
    public List<CategoryInfoDto> findAllCategories() {
        return this.categoryRepository
                .findAllOrderedByNumberOfProducts()
                .stream()
                .map(objects -> {
                    CategoryInfoDto categoryInfoDto = new CategoryInfoDto();

                    categoryInfoDto.setCategory((String) objects[0]);
                    categoryInfoDto.setProductsCount(Integer.parseInt(String.valueOf(objects[1])));
                    categoryInfoDto.setAveragePrice(new BigDecimal(String.valueOf(objects[2])));
                    categoryInfoDto.setTotalRevenue(new BigDecimal(String.valueOf(objects[3])));

                    return categoryInfoDto;
                })
                .collect(Collectors.toList());
    }
}

