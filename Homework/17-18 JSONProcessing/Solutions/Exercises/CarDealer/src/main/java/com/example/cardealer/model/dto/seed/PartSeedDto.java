package com.example.cardealer.model.dto.seed;

import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class PartSeedDto {

    @Expose
    private String name;

    @Expose
    private BigDecimal price;

    @Expose
    private int quantity;
}
