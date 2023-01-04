package com.example.advquerying.repositories;

import com.example.advquerying.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    Optional<Ingredient> findByName(String name);

    List<Ingredient> findAllByNameIn(Iterable<String> names);

    List<Ingredient> findAllByNameStartingWith(String prefix);

    List<Ingredient> findAllByNameInOrderByPrice(List<String> ingredientNames);

    @Modifying
    int deleteIngredientByName(@Param("name") String name);

    @Modifying
    @Query("UPDATE Ingredient AS i SET i.price = i.price * :percentage WHERE i.name = :name")
    int increasePriceByName(@Param("name") String name,
                            @Param("percentage") BigDecimal percentage);

    @Modifying
    @Query("UPDATE Ingredient AS i SET i.price = i.price * :percentage WHERE i.name IN :names")
    int increasePriceByNames(@Param("names") Iterable<String> names,
                             @Param("percentage") BigDecimal percentage);
}
