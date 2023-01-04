package com.example.cardealer.model.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "cars")
public class Car extends BaseEntity {

    private String make;

    private String model;

    private long travelledDistance;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Part> parts;
}
