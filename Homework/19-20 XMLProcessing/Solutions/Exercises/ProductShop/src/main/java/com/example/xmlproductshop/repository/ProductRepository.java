package com.example.xmlproductshop.repository;

import com.example.xmlproductshop.model.entity.Product;
import com.example.xmlproductshop.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByPriceBetweenAndBuyerIsNullOrderByPrice(BigDecimal lower, BigDecimal upper);

    Set<Product> findAllBySellerAndBuyerNotNull(User seller);
}
