package com.example.jsonprocessingexercise.model.dto;

import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class CategoryInfoDto {

    @Expose
    private String category;

    @Expose
    private int productsCount;

    @Expose
    private BigDecimal averagePrice;

    @Expose
    private BigDecimal totalRevenue;
}
