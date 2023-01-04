package com.example.cardealer.model.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@AllArgsConstructor
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "suppliers")
public class Supplier extends BaseEntity {

    private String name;

    private boolean isImporter;
}
