package com.example.cardealer.model.dto.seed;

import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class SupplierSeedDto {

    @Expose
    private String name;

    @Expose
    private boolean isImporter;
}
