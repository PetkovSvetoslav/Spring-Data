package com.example.automappingobjects.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class EmployeeWithManagerDto extends EmployeeDto {
    private String managerLastName;

    @Override
    public String toString() {
        return super.toString() + " - Manager: " +
                (this.managerLastName == null
                        ? "[no manager]"
                        : this.managerLastName);
    }
}
