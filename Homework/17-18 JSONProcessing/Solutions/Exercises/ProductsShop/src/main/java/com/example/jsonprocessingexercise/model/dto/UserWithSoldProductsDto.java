package com.example.jsonprocessingexercise.model.dto;

import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@Data
public class UserWithSoldProductsDto {

    @Expose
    private String firstName;

    @Expose
    private String lastName;

    @Expose
    private Set<ProductsWithBuyerDto> soldProducts;
}
