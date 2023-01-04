package com.example.jsonprocessingexercise.model.dto.users_and_products;

import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class SoldProductsDto {

    @Expose
    private int count;

    @Expose
    private List<ProductDto> products;
}
