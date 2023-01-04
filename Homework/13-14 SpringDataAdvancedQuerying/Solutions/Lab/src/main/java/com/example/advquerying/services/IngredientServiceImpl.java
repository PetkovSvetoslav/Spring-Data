package com.example.advquerying.services;

import com.example.advquerying.entities.Ingredient;
import com.example.advquerying.repositories.IngredientRepository;
import com.example.advquerying.repositories.ShampooRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final ShampooRepository shampooRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository, ShampooRepository shampooRepository) {
        this.ingredientRepository = ingredientRepository;
        this.shampooRepository = shampooRepository;
    }

    @Transactional
    @Override
    public int deleteIngredientByName(String name) {
        Ingredient ingredientToDelete = this.ingredientRepository.findByName(name)
                .orElseThrow(
                        () -> new IllegalArgumentException("There is no ingredient with such a name.")
                );

        this.shampooRepository.findAllByIngredient(ingredientToDelete)
                .forEach(shampoo -> shampoo.getIngredients().remove(ingredientToDelete));

        return this.ingredientRepository.deleteIngredientByName(name);
    }

    @Transactional
    @Override
    public int increasePriceByTenPercent(String name) {
        return this.ingredientRepository.increasePriceByName(name, new BigDecimal("1.1"));
    }

    /**
     * @param names
     * @param percentage use percents like normal {95.75, 65, 2.4 ...}
     * @return the count of updated ingredients
     */
    @Transactional
    @Override
    public int increasePriceByNames(Iterable<String> names, BigDecimal percentage) {
        return this.ingredientRepository.increasePriceByNames(names,
                percentage
                        .divide(new BigDecimal(100), 4, RoundingMode.CEILING)
                        .add(new BigDecimal("1"))
        );
    }
}
