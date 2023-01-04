package com.example.jsonprocessingexercise.init;

import com.example.jsonprocessingexercise.model.dto.CategoryInfoDto;
import com.example.jsonprocessingexercise.model.dto.ProductNamePriceAndSellerDto;
import com.example.jsonprocessingexercise.model.dto.UserWithSoldProductsDto;
import com.example.jsonprocessingexercise.model.dto.users_and_products.UsersCountDto;
import com.example.jsonprocessingexercise.service.CategoryService;
import com.example.jsonprocessingexercise.service.ProductService;
import com.example.jsonprocessingexercise.service.UserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private static final String OUTPUT_PATH = "src/main/resources/files/out/";
    private static final String PRODUCTS_IN_RANGE = "products-in-range.json";
    private static final String USER_SOLD_PRODUCTS = "users-sold-products.json";
    private static final String CATEGORIES_BY_PRODUCTS = "categories-by-products.json";
    private static final String USERS_AND_PRODUCTS = "users-and-products.json";

    private final UserService userService;
    private final CategoryService categoryService;
    private final ProductService productService;
    private final Gson gson;
    private final BufferedReader reader;

    @Autowired
    public CommandLineRunnerImpl(UserService userService, CategoryService categoryService, ProductService productService, Gson gson) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.productService = productService;
        this.gson = gson;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run(String... args) throws Exception {
        this.seedData();

        System.out.println("Enter exercise number:");

        int exerciseNum = Integer.parseInt(reader.readLine());

        switch (exerciseNum) {
            case 1:
                this.productsInRange();
                break;
            case 2:
                this.successfullySoldProducts();
                break;
            case 3:
                this.categoriesByProductsCount();
                break;
            case 4:
                this.usersAndProducts();
                break;
        }
    }

    private void seedData() throws IOException {
        this.userService.seedUsers();
        this.categoryService.seedCategories();
        this.productService.seedProducts();
    }

    private void productsInRange() throws IOException {
        List<ProductNamePriceAndSellerDto> productDtos = this.productService
                .productsInRange(BigDecimal.valueOf(500), BigDecimal.valueOf(1000));

        String json = gson.toJson(productDtos);

        this.writeToFile(OUTPUT_PATH + PRODUCTS_IN_RANGE, json);
    }

    private void successfullySoldProducts() throws IOException {
        List<UserWithSoldProductsDto> sellersDtos = this.userService
                .findAllSellersWithMoreThanOneSoldProduct();

        String json = gson.toJson(sellersDtos);

        this.writeToFile(OUTPUT_PATH + USER_SOLD_PRODUCTS, json);
    }

    private void categoriesByProductsCount() throws IOException {
        List<CategoryInfoDto> categoriesInfoDtos = this.categoryService.findAllCategories();

        String json = gson.toJson(categoriesInfoDtos);

        this.writeToFile(OUTPUT_PATH + CATEGORIES_BY_PRODUCTS, json);
    }

    private void usersAndProducts() throws IOException {
        UsersCountDto allSellersDto = this.userService.findAllSellersWithTheirSoldProducts();

        String json = gson.toJson(allSellersDto);

        this.writeToFile(OUTPUT_PATH + USERS_AND_PRODUCTS, json);
    }

    private void writeToFile(String filePath, String content) throws IOException {
        Files.write(
                Path.of(filePath),
                Collections.singleton(content)
        );
    }
}
