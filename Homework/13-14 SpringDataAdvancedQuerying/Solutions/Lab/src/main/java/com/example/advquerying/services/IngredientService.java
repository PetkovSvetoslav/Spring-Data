package com.example.advquerying.services;

import java.math.BigDecimal;

public interface IngredientService {
    int deleteIngredientByName(String name);

    int increasePriceByTenPercent(String name);

    int increasePriceByNames(Iterable<String> names, BigDecimal percentage);
}
