package com.example.cardealer.model.dto;

import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class PartDto {

    @Expose
    private String Name;

    @Expose
    private BigDecimal Price;
}
