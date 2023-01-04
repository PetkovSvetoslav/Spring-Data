package com.example.automappingobjects.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Iterator;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ManagerDto extends BasicEmployeeDto {
    private List<EmployeeDto> subordinates;

    @Override
    public String toString() {
        return super.getFirstName() + " " + super.getLastName() + " | " + "Employees: " + this.getSubordinates().size() + System.lineSeparator() +
                this.getSubordinatesAsString();
    }

    private String getSubordinatesAsString() {
        StringBuilder out = new StringBuilder();

        Iterator<EmployeeDto> iterator = this.subordinates.iterator();

        while (iterator.hasNext()) {
            out.append("    - ").append(iterator.next());

            if (iterator.hasNext()) {
                out.append(System.lineSeparator());
            }
        }

        return out.toString();
    }
}
