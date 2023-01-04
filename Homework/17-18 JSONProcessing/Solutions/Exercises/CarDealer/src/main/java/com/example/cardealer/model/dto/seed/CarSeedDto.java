package com.example.cardealer.model.dto.seed;

import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CarSeedDto {

    @Expose
    private String make;

    @Expose
    private String model;

    @Expose
    private long travelledDistance;
}
