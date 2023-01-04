package com.example.jsonprocessingexercise.repository;

import com.example.jsonprocessingexercise.model.entity.Product;
import com.example.jsonprocessingexercise.model.entity.User;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends BaseRepository<Product> {

    List<Product> findAllByPriceBetweenAndBuyerIsNullOrderByPriceDesc(BigDecimal lower, BigDecimal upper);

    List<Product> findAllBySellerAndBuyerNotNull(User seller);
}
