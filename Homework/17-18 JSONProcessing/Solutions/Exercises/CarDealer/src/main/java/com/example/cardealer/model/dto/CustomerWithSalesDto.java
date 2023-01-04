package com.example.cardealer.model.dto;

import com.example.cardealer.model.entity.Sale;
import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Data
public class CustomerWithSalesDto {

    @Expose
    private long Id;

    @Expose
    private String Name;

    @Expose
    private String BirthDate;

    @Expose
    private boolean IsYoungDriver;

    @Expose
    private Set<Sale> Sales = new HashSet<>();
}
