package com.example.xmlproductshop.service.impl;

import com.example.xmlproductshop.model.dto.CategoryInfoDto;
import com.example.xmlproductshop.model.dto.seed.CategorySeedDto;
import com.example.xmlproductshop.model.entity.Category;
import com.example.xmlproductshop.repository.CategoryRepository;
import com.example.xmlproductshop.service.CategoryService;
import com.example.xmlproductshop.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper mapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               ValidationUtil validationUtil, ModelMapper mapper) {

        this.categoryRepository = categoryRepository;
        this.validationUtil = validationUtil;
        this.mapper = mapper;
    }

    @Override
    public void seedCategories(List<CategorySeedDto> categories) {
        categories
                .stream()
                .filter(validationUtil::isValid)
                .map(categorySeedDto -> mapper.map(categorySeedDto, Category.class))
                .forEach(categoryRepository::save);
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
    public List<CategoryInfoDto> findAllCategoriesInfo() {
        return this.categoryRepository
                .findAllOrderedByNumberOfProducts()
                .stream()
                .map(objects -> {
                    CategoryInfoDto categoryInfoDto = new CategoryInfoDto();

                    categoryInfoDto.setName((String) objects[0]);
                    categoryInfoDto.setProductsCount(Integer.parseInt(String.valueOf(objects[1])));
                    categoryInfoDto.setAveragePrice(new BigDecimal(String.valueOf(objects[2])));
                    categoryInfoDto.setTotalRevenue(new BigDecimal(String.valueOf(objects[3])));

                    return categoryInfoDto;
                })
                .collect(Collectors.toList());
    }
}
