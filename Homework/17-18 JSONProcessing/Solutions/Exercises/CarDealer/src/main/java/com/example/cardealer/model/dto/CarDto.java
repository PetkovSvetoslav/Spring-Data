package com.example.cardealer.model.dto;

import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CarDto {

    @Expose
    private String Make;

    @Expose
    private String Model;

    @Expose
    private long TravelledDistance;
}
