package com.example.cardealer.model.dto;

import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CarWithIdDto {

    @Expose
    private long Id;

    @Expose
    private String Make;

    @Expose
    private String Model;

    @Expose
    private long TravelledDistance;
}
