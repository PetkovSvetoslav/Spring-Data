package com.example.jsonprocessingexercise.repository;

import com.example.jsonprocessingexercise.model.entity.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends BaseRepository<Category> {

    @Query("SELECT c.name, SIZE (c.products), AVG (p.price), SUM(p.price) " +
            "FROM Category AS c " +
            "JOIN c.products AS p " +
            "GROUP BY c.name " +
            "ORDER BY SIZE (c.products) DESC")
    List<Object[]> findAllOrderedByNumberOfProducts();
}
