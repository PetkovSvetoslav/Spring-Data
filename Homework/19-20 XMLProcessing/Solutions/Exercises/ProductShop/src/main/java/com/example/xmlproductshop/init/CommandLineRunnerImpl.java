package com.example.xmlproductshop.init;

import com.example.xmlproductshop.model.dto.*;
import com.example.xmlproductshop.model.dto.seed.CategoriesSeedRootDto;
import com.example.xmlproductshop.model.dto.seed.ProductsSeedRootDto;
import com.example.xmlproductshop.model.dto.seed.UsersSeedRootDto;
import com.example.xmlproductshop.service.CategoryService;
import com.example.xmlproductshop.service.ProductService;
import com.example.xmlproductshop.service.UserService;
import com.example.xmlproductshop.util.XmlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.math.BigDecimal;
import java.util.List;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private static final String RESOURCES_FILES_PATH = "src/main/resources/files/";
    private static final String CATEGORIES_FILE_NAME = "categories.xml";
    private static final String USERS_FILE_NAME = "users.xml";
    private static final String PRODUCTS_FILE_NAME = "products.xml";

    private static final String OUTPUT_PATH = RESOURCES_FILES_PATH + "out/";
    private static final String PRODUCTS_IN_RANGE = "products-in-range.xml";
    private static final String USER_SOLD_PRODUCTS = "users-sold-products.xml";
    private static final String CATEGORIES_BY_PRODUCTS = "categories-by-products.xml";
    private static final String USERS_AND_PRODUCTS = "users-and-products.xml";

    private final CategoryService categoryService;
    private final UserService userService;
    private final ProductService productService;
    private final XmlParser xmlParser;

    @Autowired
    public CommandLineRunnerImpl(CategoryService categoryService, UserService userService,
                                 ProductService productService, XmlParser xmlParser) {

        this.categoryService = categoryService;
        this.userService = userService;
        this.productService = productService;
        this.xmlParser = xmlParser;
    }

    @Override
    public void run(String... args) throws Exception {
        this.seedData();

        this.productsInRange();
        this.successfullySoldProducts();
        this.categoriesByProductsCount();
        this.usersAndProducts();
    }

    private void seedData() throws JAXBException {
        // Categories seeding
        CategoriesSeedRootDto categoriesSeedRootDto =
                xmlParser.fromFile(
                        RESOURCES_FILES_PATH + CATEGORIES_FILE_NAME, CategoriesSeedRootDto.class
                );

        categoryService.seedCategories(categoriesSeedRootDto.getCategories());

        // Users seeding
        UsersSeedRootDto usersSeedRootDto =
                xmlParser.fromFile(
                        RESOURCES_FILES_PATH + USERS_FILE_NAME, UsersSeedRootDto.class
                );

        userService.seedUsers(usersSeedRootDto.getUsers());

        // Products seeding
        ProductsSeedRootDto productsSeedRootDto = xmlParser.fromFile(
                RESOURCES_FILES_PATH + PRODUCTS_FILE_NAME, ProductsSeedRootDto.class
        );

        productService.seedProducts(productsSeedRootDto.getProducts());
    }

    private void productsInRange() throws JAXBException {
        List<ProductWithSellerDto> productDtos = productService
                .productsInRangeWithoutBuyers(new BigDecimal("500"), new BigDecimal("1000"));

        ProductWithSellerRootDto rootDto = new ProductWithSellerRootDto();
        rootDto.setProducts(productDtos);

        xmlParser.writeToFile(OUTPUT_PATH + PRODUCTS_IN_RANGE, rootDto);
    }

    private void successfullySoldProducts() throws JAXBException {
        List<UserWithSoldProductsDto> users = userService
                .findAllUsersWithMoreThanOneSoldProduct();

        UsersWithSoldProductsRootDto rootDto = new UsersWithSoldProductsRootDto(users);

        xmlParser.writeToFile(OUTPUT_PATH + USER_SOLD_PRODUCTS, rootDto);
    }

    private void categoriesByProductsCount() throws JAXBException {
        List<CategoryInfoDto> categoriesInfo = categoryService
                .findAllCategoriesInfo();

        CategoriesInfoRootDto rootDto = new CategoriesInfoRootDto(categoriesInfo);

        xmlParser.writeToFile(OUTPUT_PATH + CATEGORIES_BY_PRODUCTS, rootDto);
    }

    private void usersAndProducts() throws JAXBException {
        List<UserWithProductsDto> users = userService.findAllUsersWithSoldProducts();

        UsersWithProductsRootDto rootDto = new UsersWithProductsRootDto(users.size(), users);

        xmlParser.writeToFile(OUTPUT_PATH + USERS_AND_PRODUCTS, rootDto);
    }
}
