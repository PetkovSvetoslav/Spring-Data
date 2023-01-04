package com.example.xmlproductshop.service;

import com.example.xmlproductshop.model.dto.ProductWithSellerDto;
import com.example.xmlproductshop.model.dto.seed.ProductSeedDto;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    void seedProducts(List<ProductSeedDto> products);

    List<ProductWithSellerDto> productsInRangeWithoutBuyers(BigDecimal lower, BigDecimal upper);
}
