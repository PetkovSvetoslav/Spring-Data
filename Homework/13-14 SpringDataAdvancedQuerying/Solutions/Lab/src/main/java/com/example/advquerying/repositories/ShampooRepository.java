package com.example.advquerying.repositories;

import com.example.advquerying.entities.Ingredient;
import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ShampooRepository extends JpaRepository<Shampoo, Long> {
    List<Shampoo> findAllBySizeOrderById(Size size);

    List<Shampoo> findAllBySizeOrLabelIdOrderByPrice(Size size, long labelId);

    List<Shampoo> findAllByPriceAfterOrderByPriceDesc(BigDecimal price);

    int countByPriceBefore(BigDecimal price);

    @Query("SELECT s FROM Shampoo AS s, IN(s.ingredients) AS i WHERE i = :ingredient")
    List<Shampoo> findAllByIngredient(@Param("ingredient") Ingredient ingredient);

    /*
    @Query(value = "SELECT * " +
        "FROM shampoos AS s " +
        "JOIN shampoos_ingredients si on s.id = si.shampoo_id " +
        "JOIN ingredients i on i.id = si.ingredient_id " +
        "WHERE i.name IN (:names) " +
        "GROUP BY s.brand", nativeQuery = true)
     */
    @Query("SELECT s FROM Ingredient AS i JOIN i.shampoos AS s WHERE i.name IN :names GROUP BY s.brand")
    List<Shampoo> findAllByIngredientNames(@Param("names") Iterable<String> ingredientNames);

    @Query("SELECT s FROM Shampoo AS s WHERE SIZE(s.ingredients) < :count")
    List<Shampoo> findAllByIngredientsCountBefore(@Param("count") int count);
}
