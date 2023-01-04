package com.example.nextleveltechnologies.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "projects")
public class Project extends BaseEntity{

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    private Boolean isFinished;

    @Column(nullable = false)
    private BigDecimal payment;

    private LocalDate startDate;

    @ManyToOne(optional = false)
    private Company company;
}
