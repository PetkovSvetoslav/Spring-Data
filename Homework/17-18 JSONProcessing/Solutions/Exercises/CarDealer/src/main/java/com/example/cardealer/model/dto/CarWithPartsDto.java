package com.example.cardealer.model.dto;

import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class CarWithPartsDto {

    @Expose
    private String Make;

    @Expose
    private String Model;

    @Expose
    private long TravelledDistance;

    @Expose
    private List<PartDto> parts;
}
