package com.example.nextleveltechnologies.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
public class ProjectViewDto {
    private String name;
    private String description;
    private BigDecimal payment;

    @Override
    public String toString() {
        return "Project Name: " + name + System.lineSeparator() +
                "   Description: " + description + System.lineSeparator() +
                "   " + payment;
    }
}
