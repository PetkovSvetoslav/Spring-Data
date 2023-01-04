package com.example.cardealer.model.dto;

import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class SupplierWithCountOfPartsDto {

    @Expose
    private long Id;

    @Expose
    private String Name;

    @Expose
    private int partsCount;
}
