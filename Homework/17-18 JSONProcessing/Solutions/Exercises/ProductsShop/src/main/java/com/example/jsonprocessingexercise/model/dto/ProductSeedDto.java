package com.example.jsonprocessingexercise.model.dto;

import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class ProductSeedDto {

    @Expose
    @Size(min = 3)
    private String name;

    @Expose
    @NotNull
    @Positive
    private BigDecimal price;
}
