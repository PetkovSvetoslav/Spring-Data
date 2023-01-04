package com.example.advquerying.init;

import com.example.advquerying.entities.Ingredient;
import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import com.example.advquerying.repositories.IngredientRepository;
import com.example.advquerying.repositories.ShampooRepository;
import com.example.advquerying.services.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Component
public class AppInitializer implements CommandLineRunner {

    private final IngredientService ingredientService;
    private final ShampooRepository shampooRepository;
    private final IngredientRepository ingredientRepository;

    @Autowired
    public AppInitializer(IngredientService ingredientService, ShampooRepository shampooRepository, IngredientRepository ingredientRepository) {
        this.ingredientService = ingredientService;
        this.shampooRepository = shampooRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter the number of the exercise: ");
        int exerciseNum = Integer.parseInt(reader.readLine());

        switch (exerciseNum) {
            case 1 -> this.selectShampoosBySize();
            case 2 -> this.selectShampoosBySizeOrLabel();
            case 3 -> this.selectShampoosByPrice();
            case 4 -> this.selectIngredientsByName();
            case 5 -> this.selectIngredientsByNames();
            case 6 -> this.countByPrice();
            case 7 -> this.selectShampoosByIngredients();
            case 8 -> this.selectShampoosByIngredientsCount();
            case 9 -> this.deleteIngredientsByName();
            case 10 -> this.updateIngredientsByPrice();
            case 11 -> this.updateIngredientsByNames();
        }
    }

    // 1. Select Shampoos by Size
    private void selectShampoosBySize() {
        this.shampooRepository.findAllBySizeOrderById(Size.MEDIUM)
                .forEach(this::printShampoo);
    }

    // 2. Select Shampoos by Size or Label
    private void selectShampoosBySizeOrLabel() {
        this.shampooRepository.findAllBySizeOrLabelIdOrderByPrice(Size.MEDIUM, 10)
                .forEach(this::printShampoo);
    }

    // 3. Select Shampoos by Price
    private void selectShampoosByPrice() {
        this.shampooRepository.findAllByPriceAfterOrderByPriceDesc(new BigDecimal("5"))
                .forEach(this::printShampoo);
    }

    // 4. Select Ingredients by Name
    private void selectIngredientsByName() {
        this.ingredientRepository.findAllByNameStartingWith("M")
                .forEach(this::printIngredient);
    }

    // 5. Select Ingredients by Names
    private void selectIngredientsByNames() {
        List<String> ingredientNames = List.of("Lavender", "Herbs", "Apple");

        this.ingredientRepository.findAllByNameInOrderByPrice(ingredientNames)
                .forEach(this::printIngredient);
    }

    // 6. Count Shampoos by Price
    private void countByPrice() {
        System.out.println("count: " +
                this.shampooRepository.countByPriceBefore(new BigDecimal("8.50"))
        );
    }

    // 7. Select Shampoos by Ingredients
    private void selectShampoosByIngredients() {
        List<String> ingredientNames = List.of("Berry", "Mineral-Collagen");

        this.shampooRepository.findAllByIngredientNames(ingredientNames)
                .forEach(this::printShampoo);
    }

    // 8. Select Shampoos by Ingredients Count
    private void selectShampoosByIngredientsCount() {
        this.shampooRepository.findAllByIngredientsCountBefore(2)
                .forEach(this::printShampoo);
    }

    // 9. Delete Ingredients by Name
    private void deleteIngredientsByName() {
        String nameToDelete = "Apple";

        System.out.println("Number of deleted ingredients is: " +
                this.ingredientService.deleteIngredientByName(nameToDelete)
        );
    }

    // 10. Update Ingredients by Price
    private void updateIngredientsByPrice() {
        String ingredientName = "Herbs";

        System.out.println("Before:");
        this.printIngredient(
                this.ingredientRepository.findByName(ingredientName).get()
        );

        this.ingredientService.increasePriceByTenPercent(ingredientName);

        System.out.println("After:");
        this.printIngredient(
                this.ingredientRepository.findByName(ingredientName).get()
        );
    }

    // 11. Update Ingredients by Names
    private void updateIngredientsByNames() {
        Set<String> names = Set.of("Apple", "Lavender");

        System.out.println("Before:");
        this.ingredientRepository.findAllByNameIn(names)
                .forEach(this::printIngredient);

        System.out.println("Number of updated ingredients is: " +
                this.ingredientService
                        .increasePriceByNames(names, new BigDecimal("100"))
        );

        System.out.println("After:");
        this.ingredientRepository.findAllByNameIn(names)
                .forEach(this::printIngredient);
    }

    //==================================================================================================================

    private void printShampoo(Shampoo shampoo) {
        System.out.printf("%3d | %-50.50s | %slv.%n",
                shampoo.getId(),
                shampoo.getBrand(),
                shampoo.getPrice());
    }

    private void printIngredient(Ingredient ingredient) {
        System.out.printf("%3d | %-20.20s | %slv.%n",
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getPrice());
    }
}
