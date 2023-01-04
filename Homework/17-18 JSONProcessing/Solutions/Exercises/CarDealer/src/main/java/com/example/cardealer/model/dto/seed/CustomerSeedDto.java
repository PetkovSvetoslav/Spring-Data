package com.example.cardealer.model.dto.seed;

import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CustomerSeedDto {

    @Expose
    private String name;

    @Expose
    private String birthDate;

    @Expose
    private boolean isYoungDriver;
}
