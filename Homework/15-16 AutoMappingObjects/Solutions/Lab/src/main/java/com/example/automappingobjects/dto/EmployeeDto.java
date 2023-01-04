package com.example.automappingobjects.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class EmployeeDto extends BasicEmployeeDto {
    private double salary;

    @Override
    public String toString() {
        return String.format("%s %s %.2f",
                super.getFirstName(),
                super.getLastName(),
                this.getSalary());
    }
}
