package com.example.cardealer.model.dto;

import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class SaleInfoDto {

    @Expose
    private CarDto car;

    @Expose
    private String customerName;

    @Expose
    private BigDecimal Discount;

    @Expose
    private BigDecimal price;

    @Expose
    private BigDecimal priceWithDiscount;
}
