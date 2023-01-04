package com.example.nextleveltechnologies.model.dto.seed;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@NoArgsConstructor
@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class EmployeeSeedDto {

    @NotBlank
    @XmlElement(name = "first-name")
    private String firstName;

    @NotBlank
    @XmlElement(name = "last-name")
    private String lastName;

    @NotNull
    @PositiveOrZero
    @XmlElement(name = "age")
    private Byte age;

    @XmlElement(name = "project")
    private ProjectSeedDto project;
}
