package com.example.nextleveltechnologies.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "companies")
public class Company extends BaseEntity{

    @Column(nullable = false)
    private String name;
}
