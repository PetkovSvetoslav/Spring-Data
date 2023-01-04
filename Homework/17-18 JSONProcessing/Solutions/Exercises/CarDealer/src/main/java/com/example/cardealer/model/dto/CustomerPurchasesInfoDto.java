package com.example.cardealer.model.dto;

import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class CustomerPurchasesInfoDto {

    @Expose
    private String fullName;

    @Expose
    private int boughtCars;

    @Expose
    private BigDecimal spentMoney;
}
