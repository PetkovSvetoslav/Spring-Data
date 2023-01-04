package com.example.jsonprocessingexercise.model.dto;

import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class ProductNamePriceAndSellerDto {

    @Expose
    private String name;

    @Expose
    private BigDecimal price;

    @Expose
    private String seller;
}
