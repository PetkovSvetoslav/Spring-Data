package com.example.jsonprocessingexercise.service.impl;

import com.example.jsonprocessingexercise.model.dto.ProductNamePriceAndSellerDto;
import com.example.jsonprocessingexercise.model.dto.ProductSeedDto;
import com.example.jsonprocessingexercise.model.entity.Category;
import com.example.jsonprocessingexercise.model.entity.Product;
import com.example.jsonprocessingexercise.model.entity.User;
import com.example.jsonprocessingexercise.repository.ProductRepository;
import com.example.jsonprocessingexercise.service.CategoryService;
import com.example.jsonprocessingexercise.service.ProductService;
import com.example.jsonprocessingexercise.service.UserService;
import com.example.jsonprocessingexercise.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.jsonprocessingexercise.constant.GlobalConstant.RESOURCES_FILE_PATH;

@Service
public class ProductServiceImpl implements ProductService {

    public static final String PRODUCTS_FILE_NAME = "products.json";

    private final ProductRepository productRepository;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public ProductServiceImpl(ProductRepository productRepository,
                              UserService userService, CategoryService categoryService,
                              ModelMapper mapper, ValidationUtil validationUtil, Gson gson) {

        this.productRepository = productRepository;
        this.userService = userService;
        this.categoryService = categoryService;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public void seedProducts() throws IOException {
        String fileContent = Files.readString(Path.of(RESOURCES_FILE_PATH + PRODUCTS_FILE_NAME));

        ProductSeedDto[] productSeedDtos = gson.fromJson(fileContent, ProductSeedDto[].class);

        List<Product> products = Arrays.stream(productSeedDtos)
                .filter(validationUtil::isValid)
                .map(productSeedDto -> mapper.map(productSeedDto, Product.class))
                .collect(Collectors.toList());

        setRandomSellerAndBuyer(products);
        setRandomCategories(products);

        this.productRepository.saveAll(products);
    }

    @Override
    public List<ProductNamePriceAndSellerDto> productsInRange(BigDecimal lower, BigDecimal upper) {
        List<Product> products = this.productRepository
                .findAllByPriceBetweenAndBuyerIsNullOrderByPriceDesc(lower, upper);

        List<ProductNamePriceAndSellerDto> result = new ArrayList<>();

        for (Product product : products) {
            ProductNamePriceAndSellerDto productDto = mapper.map(product, ProductNamePriceAndSellerDto.class);
            productDto.setSeller(product.getSeller().getFirstName() + " " + product.getSeller().getLastName());
            result.add(productDto);
        }

        return result;
    }

    private void setRandomSellerAndBuyer(List<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);

            User seller = this.userService.getRandomUser();
            product.setSeller(seller);

            if (i % 3 == 0) {
                User buyer = this.userService.getRandomUser();

                if (buyer.equals(seller)) {
                    product.setBuyer(null);
                } else {
                    product.setBuyer(buyer);
                }
            }
        }
    }

    private void setRandomCategories(List<Product> products) {
        for (Product product : products) {
            Set<Category> randomCategories = this.categoryService
                    .getRandomCategories(3);

            product.setCategories(randomCategories);
        }
    }
}
