package com.example.nextleveltechnologies.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmployeeViewDto {
    private String name;
    private Byte age;
    private String projectName;

    @Override
    public String toString() {
        return "Name: " + name + System.lineSeparator() +
                "   Age: " + age + System.lineSeparator() +
                "   Project name: " + projectName;
    }
}
