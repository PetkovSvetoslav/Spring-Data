package com.example.jsonprocessingexercise.service;

import com.example.jsonprocessingexercise.model.dto.ProductNamePriceAndSellerDto;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    void seedProducts() throws IOException;

    List<ProductNamePriceAndSellerDto> productsInRange(BigDecimal lower, BigDecimal upper);
}
