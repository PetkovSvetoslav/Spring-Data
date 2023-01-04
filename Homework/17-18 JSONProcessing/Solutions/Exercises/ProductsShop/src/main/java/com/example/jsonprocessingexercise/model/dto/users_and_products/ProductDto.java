package com.example.jsonprocessingexercise.model.dto.users_and_products;

import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class ProductDto {

    @Expose
    private String name;

    @Expose
    private BigDecimal price;
}
