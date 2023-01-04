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
import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class ProjectSeedDto {

    @NotBlank
    @XmlElement(name = "name")
    private String name;

    @NotBlank
    @XmlElement(name = "description")
    private String description;

    @NotNull
    @XmlElement(name = "start-date")
    private String startDate;

    @NotNull
    @XmlElement(name = "is-finished")
    private Boolean isFinished;

    @PositiveOrZero
    @XmlElement(name = "payment")
    private BigDecimal payment;

    @NotNull
    @XmlElement(name = "company")
    private CompanySeedDto company;
}
