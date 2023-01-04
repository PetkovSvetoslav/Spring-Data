package com.example.xmlproductshop.service.impl;

import com.example.xmlproductshop.model.dto.ProductWithSellerDto;
import com.example.xmlproductshop.model.dto.seed.ProductSeedDto;
import com.example.xmlproductshop.model.entity.Category;
import com.example.xmlproductshop.model.entity.Product;
import com.example.xmlproductshop.model.entity.User;
import com.example.xmlproductshop.repository.ProductRepository;
import com.example.xmlproductshop.service.CategoryService;
import com.example.xmlproductshop.service.ProductService;
import com.example.xmlproductshop.service.UserService;
import com.example.xmlproductshop.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final CategoryService categoryService;
    private final UserService userService;
    private final ProductRepository productRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper mapper;

    @Autowired
    public ProductServiceImpl(CategoryService categoryService, UserService userService, ProductRepository productRepository,
                              ValidationUtil validationUtil, ModelMapper mapper) {

        this.categoryService = categoryService;
        this.userService = userService;
        this.productRepository = productRepository;
        this.validationUtil = validationUtil;
        this.mapper = mapper;
    }

    @Override
    public void seedProducts(List<ProductSeedDto> productSeedDtos) {
        List<Product> products = productSeedDtos
                .stream()
                .filter(validationUtil::isValid)
                .map(productSeedDto -> mapper.map(productSeedDto, Product.class))
                .collect(Collectors.toList());

        setRandomSellerAndBuyer(products);
        setRandomCategories(products);

        this.productRepository.saveAll(products);
    }

    @Override
    public List<ProductWithSellerDto> productsInRangeWithoutBuyers(BigDecimal lower, BigDecimal upper) {
        List<Product> products = this.productRepository
                .findAllByPriceBetweenAndBuyerIsNullOrderByPrice(lower, upper);

        return products
                .stream()
                .map(product -> mapper.map(product, ProductWithSellerDto.class))
                .collect(Collectors.toList());
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
